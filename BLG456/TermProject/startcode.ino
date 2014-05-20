/* 20140510 Mahmut Ali OZKURAN
ITU BLG 456 Spring Semester Term Project
Code snippets used from
github.com/adasta
github.com/ros-drivers/rosserial
*/

#include <ros.h>
#include <std_msgs/Empty.h>
#include <geometry_msgs/Twist.h>
#include <std_msgs/Float32.h>
#include <ros/time.h>
#include <sensor_msgs/Range.h>

//autonomous definitions start
#define ROS_SERIAL_BAUD_RATE 115200 // Rosserial baud rate 115200 bps for example

// Distance between both wheels
#define WHEEL_OFFSET_M 0.0889 // 3.5" ~ 8.89cm
#define HALF_WHEEL_OFFSET_M 0.04445 // distance from center to wheel along turning axis

#define MAX_REAL_SPEED 0.65 // m/s
#define MAX_MOTOR_SPEED 400.0 // RPM

// Others
#define BATTERY_READ_PIN 1 // (SPECIAL) Analog Pin A1
#define USER_PUSHBUTTON_PIN 12
#define LED_PIN 13

#define BATTERY_VOLTAGE_PUBLISH_RATE 1000 // ms (publish voltage every second)

// Encoders
#define ENCODER_LEFT_PIN_A 3
#define ENCODER_LEFT_PIN_B 4

#define ENCODER_RIGHT_PIN_A 2
#define ENCODER_RIGHT_PIN_B 11


#define TICKS_PER_REV 379 // ticks for one wheel revolution
#define TICKS_PER_METER 3110 // 790 ticks for 10 inches, 790 ticks per .254m ~ 3110.23622 ticks/meter
#define METERS_PER_REV 0.12186495176848874 // meters per full revolution
#define REV_PER_METER 8.20580474934037 // revolutions per meter

#define WHEEL_VEL_PUBLISH_RATE 100 // ms (10Hz)
#define MOTOR_PID_RATE 20 // ms (50Hz) PID Motor update rate (Should be < WHEEL_VEL_PUBLISH_RATE)
#define MOTOR_SPEED_CUTOFF 20 // lowest motor speed at which to zero it
//autonomous definitions end

//Start of Ultrasound
sensor_msgs::Range range_msg;
ros::Publisher pub_range( "/ultrasound", &range_msg);

const int adc_pin = 0;

char frameid[] = "/ultrasound";

float getRange_Ultrasound(int pin_num){
  int val = 0;
  for(int i=0; i<4; i++) val += analogRead(pin_num);
  float range = val;
  if (range < 10)
  {
    turnAround();  
  }
  return range /322.519685; 
}
//End of Ultrasound


#define LED_PIN 13

//Start of Motors
#define PWM_L 10
#define PWM_R 9
#define DIR_L 8
#define DIR_R 7

#if defined(__AVR_ATmega168__) || defined(__AVR_ATmega328P__) || defined (__AVR_ATmega32U4__)
#define USE_20KHZ_PWM
#endif

class ZumoMotors
{
  public:
  
    // constructor (doesn't do anything)
    ZumoMotors();
    
    // enable/disable flipping of motors
    static void flipLeftMotor(boolean flip);
    static void flipRightMotor(boolean flip);
    
    // set speed for left, right, or both motors
    static void setLeftSpeed(int speed);
    static void setRightSpeed(int speed);
    static void setSpeeds(int leftSpeed, int rightSpeed);
    
  private:

    static inline void init()
    {
      static boolean initialized = false;

      if (!initialized)
      {
        initialized = true;
        init2();
      }
    }
    
    // initializes timer1 for proper PWM generation
    static void init2();
};

static boolean flipLeft = false;
static boolean flipRight = false;

// constructor (doesn't do anything)
ZumoMotors::ZumoMotors()
{
}

// initialize timer1 to generate the proper PWM outputs to the motor drivers
void ZumoMotors::init2()
{
  pinMode(PWM_L, OUTPUT);
  pinMode(PWM_R, OUTPUT);
  pinMode(DIR_L, OUTPUT);
  pinMode(DIR_R, OUTPUT);

#ifdef USE_20KHZ_PWM
  // Timer 1 configuration
  // prescaler: clockI/O / 1
  // outputs enabled
  // phase-correct PWM
  // top of 400
  //
  // PWM frequency calculation
  // 16MHz / 1 (prescaler) / 2 (phase-correct) / 400 (top) = 20kHz
  TCCR1A = 0b10100000;
  TCCR1B = 0b00010001;
  ICR1 = 400;
#endif
}

// enable/disable flipping of left motor
void ZumoMotors::flipLeftMotor(boolean flip)
{
  flipLeft = flip;
}

// enable/disable flipping of right motor
void ZumoMotors::flipRightMotor(boolean flip)
{
  flipRight = flip;
}

// set speed for left motor; speed is a number between -400 and 400
void ZumoMotors::setLeftSpeed(int speed)
{
  init(); // initialize if necessary
    
  boolean reverse = 0;
  
  if (speed < 0)
  {
    speed = -speed; // make speed a positive quantity
    reverse = 1; // preserve the direction
  }
  if (speed > 400) // Max
    speed = 400;
#ifdef USE_20KHZ_PWM
  OCR1B = speed;
#else
  analogWrite(PWM_L, speed * 51 / 80); // default to using analogWrite, mapping 400 to 255
#endif

  if (reverse ^ flipLeft) // flip if speed was negative or flipLeft setting is active, but not both
    digitalWrite(DIR_L, HIGH);
  else
    digitalWrite(DIR_L, LOW);
}

// set speed for right motor; speed is a number between -400 and 400
void ZumoMotors::setRightSpeed(int speed)
{
  init(); // initialize if necessary
    
  boolean reverse = 0;
  
  if (speed < 0)
  {
    speed = -speed; // Make speed a positive quantity
    reverse = 1; // Preserve the direction
  }
  if (speed > 400) // Max PWM dutycycle
    speed = 400;
#ifdef USE_20KHZ_PWM
  OCR1A = speed;
#else
  analogWrite(PWM_R, speed * 51 / 80); // default to using analogWrite, mapping 400 to 255
#endif

  if (reverse ^ flipRight) // flip if speed was negative or flipRight setting is active, but not both
    digitalWrite(DIR_R, HIGH);
  else
    digitalWrite(DIR_R, LOW);
}

// set speed for both motors
void ZumoMotors::setSpeeds(int leftSpeed, int rightSpeed)
{
  setLeftSpeed(leftSpeed);
  setRightSpeed(rightSpeed);
}

//End of Motors

ros::NodeHandle  nh; 
geometry_msgs::Twist twist_msg; //Direction Messages

ZumoMotors motors;



void messageCb( const std_msgs::Empty& toggle_msg){
  digitalWrite(13, HIGH-digitalRead(13));   // blink the led
}

ros::Subscriber<std_msgs::Empty> sub("toggle_led", &messageCb );

void setup()
{ 
  pinMode(LED_PIN, OUTPUT);
  nh.initNode();
  nh.subscribe(sub);
  
  
//Ultrasound Start
  nh.advertise(pub_range);
    
  range_msg.radiation_type = sensor_msgs::Range::ULTRASOUND;
  range_msg.header.frame_id = frameid;
  range_msg.field_of_view = 0.1; 
  range_msg.min_range = 0.0;
  range_msg.max_range = 6.47;
  
  pinMode(8,OUTPUT);
  digitalWrite(8, LOW);
//Ultrasound End
}

//Turn Around Start
void turnAround()
{
  for (int speed = 0; speed <= 400; speed++)
  {
    motors.setLeftSpeed(speed);
    delay(2);
  } 
}
//Turn Around End


void loop()
{  
  digitalWrite(LED_PIN, HIGH);
  

  delay(500);
  //led blink start
  nh.spinOnce();
  //delay(1);
  //led blink end
}
