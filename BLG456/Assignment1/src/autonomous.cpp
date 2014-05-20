/* 
		ITU BLG 456 E Robotics
		Assignment 1
		Autonomous Moving Robot with Laser Scan Data Processing
		040000815
		Mahmut Ali OZKURAN
*/

#include <ros/ros.h>
#include <std_msgs/String.h>
#include <sensor_msgs/LaserScan.h>
#include <geometry_msgs/Twist.h>

bool debug = false; // Debugging variable
ros::Publisher drive_pub; //Publisher to set action move commands

void scanCallback (const sensor_msgs::LaserScan::ConstPtr& scan_msg)
{
  size_t rangessize = scan_msg->ranges.size(); // variable to store size of range array
	int twelveoclock, nineoclock, threeoclock; //variables to hold 3-9-12 o'clock directions' indexes in range array
 	double range12, range9, range3; //variables to store range to closest obstacle at 12,9 and 3 o'clock directions
	geometry_msgs::Twist cmd_msg; //Twist message


	twelveoclock = rangessize/2; // getting mid point of array index where angular position 0
	nineoclock = twelveoclock + (1.57 / scan_msg->angle_increment); // getting inde positon of 9 oclock direction
	threeoclock = twelveoclock - (1.57 / scan_msg->angle_increment); // getting index position of 3 oclock direction
	
	range12 = scan_msg->ranges[twelveoclock];
	range9 = scan_msg->ranges[nineoclock];
	range3 = scan_msg->ranges[threeoclock];

	if(range12 > 1.2) { //if not on collision route continue
		cmd_msg.linear.x = 1;
		cmd_msg.angular.z = 0;
	} 
	else if(range3 > 1) //if on collision course and 3 oclock is free turn right
	{
		cmd_msg.linear.x = 0.0;
		cmd_msg.angular.z = -1.57;
	}
	else if(range9 > 1) // if on collision course and 3 oclock not free and 9 oclock free turn left
	{
		cmd_msg.linear.x = 0.0;
		cmd_msg.angular.z = 1.57;
	}
	else // if all three options not available turn back
	{
		cmd_msg.linear.x = 0.0;
		cmd_msg.angular.z = 3.14;
	}
	drive_pub.publish(cmd_msg);
	
	if (debug) 
	{
		ROS_INFO("12 O'Clock Index : %d ",twelveoclock); 
		ROS_INFO("12 O'Clock : %f ",scan_msg->ranges[rangessize/2]); 
		ROS_INFO("Range Count : %d ",rangessize); 
		ROS_INFO("Angle Min : %f ",scan_msg->angle_min); 
		ROS_INFO("Angle Max : %f ",scan_msg->angle_max);
		ROS_INFO("Angle Increment : %f ",scan_msg->angle_increment); 
		ROS_INFO("9 O'Clock : %f ",scan_msg->ranges[nineoclock]); 
		ROS_INFO("9 O'Clock Index : %d ",nineoclock); 
		ROS_INFO("3 O'Clock : %f ",scan_msg->ranges[threeoclock]); 
		ROS_INFO("3 O'Clock Index : %d ",threeoclock); 
	}
}
 
int main(int argc, char** argv)
{
  ros::init(argc, argv, "listener");
  ros::NodeHandle node;
  ros::Subscriber scan_sub = node.subscribe("base_scan/scan", 100, scanCallback); //Subscriber node for laser scaner initialized
	drive_pub = node.advertise<geometry_msgs::Twist>("/cmd_vel", 1); //Publisher node for /cmd_vel initialized
  ros::spin();
}
