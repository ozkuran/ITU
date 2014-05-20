/*
ITU BLG 456 E Robotics
Assignment 3
EXploration using map information
040000815
Mahmut Ali OZKURAN
Based on the source code by
Dr. Damien Jade Duff
*/
#include <cstdlib>
#include <time.h>       
#include "ros/ros.h"
#include "nav_msgs/OccupancyGrid.h"
#include <sstream>
#include <vector>
#include <queue>
#include <iostream>
// MAO - 17.04.2014 Laser scan and cmd_vel Publisher Messages
#include <tf/transform_listener.h>
#include <std_msgs/String.h>
#include <sensor_msgs/LaserScan.h>
#include <geometry_msgs/Twist.h>
#include <trajectory_msgs/JointTrajectory.h>
#include <tf/transform_broadcaster.h>
#include <tf/transform_datatypes.h>
#include <geometry_msgs/PointStamped.h>
#include <geometry_msgs/PoseStamped.h>
#include <math.h>

using namespace std;

bool debug = true; // Debugging variable
ros::Publisher drive_pub; //Publisher to set action move commands
int mapWidth;
int mapHeight;
int mapMatrix[1000000];
double angularSpeed;
double linearSpeed;
int randX, randY , locationX, locationY;
bool unknown, newTarget;
double targetX, targetY;
geometry_msgs::TransformStamped msg;
double weightOfUnknown;
bool unknownLocation;
static bool mapLoaded = false;
	
double dblAngle, dblDistance;
double angular, linear;


//MAO - 17.04.2014 Laser Scan Callback
void scanCallback (const sensor_msgs::LaserScan::ConstPtr& scan_msg)
{
	size_t rangessize = scan_msg->ranges.size(); // variable to store size of range array
	int twelveoclock, nineoclock, threeoclock; //variables to hold 3-9-12 o'clock directions' indexes in range array
 	double range12; //variables to store range to closest obstacle at 12,9 and 3 o'clock directions
	geometry_msgs::Twist cmd_msg; //Twist message


	twelveoclock = rangessize/2; // getting mid point of array index where angular position 0
	nineoclock = twelveoclock + (1.57 / scan_msg->angle_increment); // getting index positon of 9 oclock direction
	threeoclock = twelveoclock - (1.57 / scan_msg->angle_increment); // getting index position of 3 oclock direction
	
	range12 = min(scan_msg->ranges[twelveoclock-1],scan_msg->ranges[twelveoclock+1]);
	

	unknownLocation = false;
	//cout << " mapWidth : " << mapWidth << endl ;
	for(int j = 0; j< mapWidth; j++){
	  //cout << "for next " << endl;
	  if(mapMatrix[(mapWidth*locationY)+locationX+j] == -1){
	    unknownLocation = true;
	    /*dblAngle = atan2(((20/mapWidth)*locationY)-((20/mapWidth)*(locationY+j)), ((20/mapWidth)*locationX)-((20/mapWidth)*(locationX+j))); // CAlculating Angle
	    dblDistance = sqrt(pow(((20/mapWidth)*locationX)-((20/mapWidth)*(locationX+j)), 2) + pow((((20/mapWidth)*locationY)-((20/mapWidth)*(locationY+j))), 2)); //Calcuılating Distance
	    */
	  }
	}
	
	if(range12 > 1.5) { //if not on collision route continue
		cmd_msg.linear.x = 1.0;
		cmd_msg.angular.z = 0.0;
		if(unknownLocation){
		  cmd_msg.angular.z = 0.2;
		}
		else{
		  cmd_msg.angular.z = -0.2;
		}

	} 
	else 
	{
	  if (weightOfUnknown > 0) 
	  {
	    cmd_msg.linear.x = 0;
	    cmd_msg.angular.z = -2;
	    if(unknownLocation){
	      cmd_msg.angular.z = -2;
	    }
	    else{
	      cmd_msg.angular.z = -1;
	    }
	  } 
	  else 
	  {
	    cmd_msg.linear.x = 0;
	    cmd_msg.angular.z = 2;
	    if(unknownLocation){
	      cmd_msg.angular.z = 2;
	    }
	    else{
	      cmd_msg.angular.z = 1;
	    }
	  }
	}
	drive_pub.publish(cmd_msg);
	
	if (!debug) 
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

void mapCallback(const nav_msgs::OccupancyGrid::ConstPtr& msg){
    vector<signed char , allocator<signed char> > map =  msg.get()->data;
    mapWidth = msg.get()->info.width;
    mapHeight = msg.get()->info.height;    
    int unknownArea=0;
    int allArea=0;
    int discoveredArea=0;
    for(size_t i=0; i< map.size(); ++i )
    {
	allArea++;
    	if(map[i]>50)
	  discoveredArea++;
	if(map[i]<0){
	  unknownArea++;
	  if((i % mapWidth) > (mapWidth / 2)) {
	    weightOfUnknown++;
	  }
	  else
	  {
	    weightOfUnknown--;
	  }
	}
	if(!mapLoaded){
	  mapMatrix[i] = map[i];
	  mapLoaded = true;
	}
    }
    ROS_WARN("[0][0]: %d",map[(384*192)+192]); //Success Measure
    ROS_WARN("Discovered obstacle area: %d",discoveredArea); //Success Measure
    ROS_WARN("Unknown area: %d",unknownArea); 
    ROS_WARN("All area: %d",allArea); 
    ROS_WARN("Pose: x=%f,y=%f,theta=%f",msg->info.origin.position.x,msg->info.origin.position.y,2*asin(msg->info.origin.orientation.z)*(msg->info.origin.orientation.w<0?-1:1));
    ROS_WARN("Map Width: %d",msg.get()->info.width);
    ROS_WARN("Map height: %d",msg.get()->info.height);
}
int main(int argc, char **argv)
{
	newTarget = true;
	srand (time(NULL));
  	ros::init(argc, argv, "searchAndDiscover");
	ros::NodeHandle n;
  	ros::NodeHandle nDrive;
  	ros::Subscriber map_reader =n.subscribe("map",3,mapCallback);


	// MAO - 17.04.2014 cmd_vel Publisher
	ros::Subscriber scan_sub = nDrive.subscribe("base_scan/scan", 100, scanCallback); //Subscriber node for laser scaner initialized
	drive_pub = nDrive.advertise<geometry_msgs::Twist>("/cmd_vel", 1); //Publisher node for /cmd_vel initialized

	tf::TransformListener listener;  
	tf::StampedTransform transform;
	ros::Rate rate(5.0);
	//while(n.ok()) {
	  cout << "Read transform" << endl;	  
	  try{
	    listener.lookupTransform("base_link", "map", ros::Time(), transform); // Position of robot related to map
	    if(debug){
	      cout << "Read transform" << endl;
	      cout << transform.getOrigin().y() << " " << transform.getOrigin().x() << endl;
	    }
	    locationX = (mapWidth/2)+(transform.getOrigin().x()/(20/mapWidth));
	    locationY = (mapHeight/2)-(transform.getOrigin().y()/(20/mapHeight));	  
	  }
	  catch (tf::TransformException ex){
	    ROS_ERROR("%s",ex.what());
	  }

	  ros::spinOnce();
	  rate.sleep();	  


  	ros::spin();
  	return 0;
}

