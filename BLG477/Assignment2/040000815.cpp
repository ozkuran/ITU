#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/highgui/highgui.hpp"
#include <iostream>
#include <stdio.h>
#include <stdlib.h> 
 
using namespace std;
using namespace cv;

Mat src, mid, dst;
uint width;
uint height;
uint averageGray;
uchar window[400];
int val = 0;



int insertionSort()
{
    int temp, i , j;
    for(i = 0; i < 400; i++){
        temp = window[i];
        for(j = i-1; j >= 0 && temp < window[j]; j--){
            window[j+1] = window[j];
        }
        window[j+1] = temp;
    }
    return window[200];
}

void lumnosity()
{
  uchar r, g, b;
  for (int x = 0; x < height - 1; x++) {
    for (int y = 0; y < width - 1; y++) {
      b = src.at<uchar>(width * 3 * x + y * 3);
      g = src.at<uchar>(width * 3 * x + y * 3 + 1);
      r = src.at<uchar>(width * 3 * x + y * 3 + 2);
      //mid.at<uchar>(x,y) = (uchar)((0.21*r) + (0.71*g) + (0.07*b));
      mid.at<uchar>(x,y) = (uchar)((0.33*r) + (0.33*g) + (0.33*b));
    }
  }  
}

void medianBlur()
{
  int i;
  for (int x = 10; x < height - 10; x++) {
    for (int y = 10; y < width - 10; y++) {
      i = 0;
      for (int a = -10; a < 10; a++) {
	for (int b = -10; b < 10; b++) {
	  window[i] = mid.at<uchar>(x+a,y+b);
	  i++;
	}
      }
      dst.at<uchar>(x,y) = insertionSort();
    }
  }
  
}
int average()
{
  double i, total;
  i = 0;
  total = 0;
  for (int x = 0; x < height - 1; x++) {
    for (int y = 0; y < width - 1; y++) {
      i++;
      total = total + mid.at<uchar>(x,y);
    }
  }    
  return (int)(total / i);
}

void threshold()
{
  for (int x = 0; x < height ; x++) {
    for (int y = 0; y < width ; y++) {
      if (mid.at<uchar>(x,y) <= averageGray) {
	mid.at<uchar>(x,y) = 0;
      }
      else
      {
	mid.at<uchar>(x,y) = 255;
      }
    }
  }        
}

void neighbours(int _x, int _y)
{
  if(_x > 0 && _y > 0 && _x <= height && _y <= width ){
    if(mid.at<uchar>(_x,_y) == 0){
      mid.at<uchar>(_x,_y) = val;
      neighbours(_x-1, _y-1);
      neighbours(_x, _y-1);
      neighbours(_x+1, _y-1);
      neighbours(_x-1, _y);
      neighbours(_x, _y);
      neighbours(_x+1, _y);
      neighbours(_x-1, _y+1);
      neighbours(_x, _y+1);
      neighbours(_x+1, _y+1);
    }
  }
}

void findNeighbours()
{
  int x,y;
  for (x = 1; x < height-1 ; x++) {
    for (y = 1; y < width-1 ; y++) {
      if (mid.at<uchar>(x,y) == 0) {
	val++;
	neighbours(x,y);
      }
    }
  }
}

int main( int argc, char** argv )
{
      src = imread( argv[1], 1 );
      width = src.cols;
      height = src.rows;
      mid.create(height,width,  CV_8UC1);
      dst.create(height,width,  CV_8UC1);
      lumnosity();
      //cvtColor(src, mid, CV_BGR2GRAY);
      //imwrite("01.jpg", mid);  //MAO - Debug Images Uncomment to see image process
      //medianBlur(); //MAO - takes too long need a better algorithm so using default medianBlur
      //mid = dst;
      medianBlur( mid, mid, 19 );    
      //imwrite("02.jpg", mid);  //MAO - Debug Images Uncomment to see image process

      averageGray = average();
      //cout << " Average : " << averageGray << endl; // MAO - Uncomment to Debug 
      threshold(); // MAO - Need better algorithm
      //threshold(mid, mid,0,255, THRESH_BINARY | THRESH_OTSU);
      //imwrite("03.jpg", mid);   //MAO - Debug Images Uncomment to see image process
      //MAO - Uncomment for Opencv implementation of finding contours
      /*   
	vector<vector<Point> > contours;
	vector<Vec4i> hierarchy;
	
	findContours( mid, contours, hierarchy, CV_RETR_TREE, CV_CHAIN_APPROX_SIMPLE, Point(0, 0) );
	cout << " Adet " << contours.size() - 1 << endl;
      */
      findNeighbours(); // MAO - Need better algorithm to solve memory leakage problems
      cout << " Object Count : " << val << endl;

      src.release();
      mid.release();
      dst.release();
      return 0;
}