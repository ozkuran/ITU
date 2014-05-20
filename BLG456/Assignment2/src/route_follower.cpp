/* 
		ITU BLG 456 E Robotics
		Assignment 2
		Route Follower Node for Robot
		040000815
		Mahmut Ali OZKURAN
*/
#include <math.h>


#include <ros/ros.h>
#include <tf/transform_listener.h>
#include <std_msgs/String.h>
#include <sensor_msgs/LaserScan.h>
#include <geometry_msgs/Twist.h>
#include <trajectory_msgs/JointTrajectory.h>
#include <arm_navigation_msgs/MultiDOFJointTrajectory.h>
#include <tf/transform_broadcaster.h>
#include <tf/transform_datatypes.h>
#include <geometry_msgs/PointStamped.h>
#include <geometry_msgs/PoseStamped.h>

using namespace std;


bool debug = false; // Debugging variable
ros::Publisher drive_pub; //Publisher to set action move commands
geometry_msgs::Pose targPose; //Position get from refree 
geometry_msgs::Twist cmd_msg; //Twist message

geometry_msgs::TransformStamped msg;

int position = 0;
arm_navigation_msgs::MultiDOFJointTrajectory anmTarget;

double linear = 0;
double angular = 0;


void routeCallback(const arm_navigation_msgs::MultiDOFJointTrajectory &msg)
{
  targPose = msg.points[position].poses[0];
  if(debug){
    cout << "x " << targPose.position.x << endl;
    cout << "y " << targPose.position.y << endl;
    cout << "z " << targPose.position.z << endl;    
  }
}


int main(int argc, char** argv){

  ros::init(argc, argv, "route_follower");
  
  tf::TransformListener listener;  
  ros::NodeHandle node;

  ros::Subscriber routeSub = node.subscribe("route_cmd", 5, routeCallback);  // subscribe to route_cmd 

 
  drive_pub = node.advertise<geometry_msgs::Twist>("/cmd_vel", 5);	  // publisher cmd_vel

  
  ros::Rate rate(5.0);
  while (node.ok()){
    tf::StampedTransform transform;
    try{
      listener.lookupTransform("/base_link", "/odom", ros::Time(), transform); // Position of robot related to odom
      if(debug){
	cout << "Read transform" << endl;
	cout << transform.getOrigin().y() << " " << transform.getOrigin().x() << endl;
      }

      tf::transformStampedTFToMsg(transform, msg); //Convertind Cartesian


      double angle = atan2((targPose.position.y-msg.transform.translation.y), (targPose.position.x-msg.transform.translation.x)); // CAlculating Angle
      //cout << "angle " << angle << endl;
      double distance = sqrt(pow(targPose.position.x-msg.transform.translation.x, 2) + pow(targPose.position.y-msg.transform.translation.y, 2)); //Calcuılating Distance
    

      if (distance > 0.1)
      {
	if (abs(angle) > 0.1) 
	{
	  if (angle > 0) 
	  {
	    angular = -1;
	    //linear = 0.1;
	  } 
	  else 
	  {
	    angular = 1;
	    //linear = 0.1;
	  }
	} 
	else 
	{
	  linear = 1;
	  angular = 0;
	}
      }
      else{
	cout << "distance " << distance << endl;	
	cout << "position " << position << endl;
	cout << "targPose.position.x " << targPose.position.x << "-" << "msg.transform.translation.x " << msg.transform.translation.x << endl;
	cout << "targPose.position.y " << targPose.position.y << "-" << "msg.transform.translation.y " << msg.transform.translation.y << endl;
	position++; //Next target
      }
      cmd_msg.angular.z = angular;
      cmd_msg.linear.x = linear;
      drive_pub.publish(cmd_msg); 
    }
    catch (tf::TransformException ex){
      ROS_ERROR("%s",ex.what());
    }

    ros::spinOnce();
    rate.sleep();
  }   
  ros::spin();
  return 0;
};
