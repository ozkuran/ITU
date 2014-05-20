#include <iostream>
#include "cv.h"
#include "highgui.h"

using namespace std;


uchar *matEdgeDirection;
float *matEdgeMagnitude;
uint width;
uint height;


IplImage *inputImage;

cv::Mat matImage,matImage2, matImage3;

//Initialize Matrices
void zeroMatrices()
{
  //Initializing Magnitude and Direction Matrices
  for (int x = 0; x < height; x++) {
	for (int y = 0; y < width; y++) {
	  matEdgeMagnitude[(x*width)+y] = 0;
	  matEdgeDirection[(x*width)+y] = 0;
	}
  }  
}

//Converting color image to b&w 
void lumnosity()
{
  uchar r, g, b;
  for (int x = 0; x < height - 1; x++) {
    for (int y = 0; y < width - 1; y++) {
      b = inputImage->imageData[inputImage->widthStep * x + y * 3];
      g = inputImage->imageData[inputImage->widthStep * x + y * 3 + 1];
      r = inputImage->imageData[inputImage->widthStep * x + y * 3 + 2];
      matImage.at<uchar>(x,y) = (uchar)((0.21*r) + (0.71*g) + (0.07*b));
    }
  }  
}

//Gaussian Blur Method
void blur()
{
  int col, row;
  long long val;
  uchar val2;
  //Gaussian Blur
  //First and last 2 row and column unprocessed
  for( row = 2; row < matImage.rows-2; row++ )
  {
    for ( col = 2; col < matImage.cols-2; col++ )
    {
      val = 0;
      //Minus 2 Row
      val = val + (2*(int)matImage.at<uchar>(row-2,col-2));
      val = val + (4*(int)matImage.at<uchar>(row-2,col-1));
      val = val + (5*(int)matImage.at<uchar>(row-2,col));
      val = val + (4*(int)matImage.at<uchar>(row-2,col+1));
      val = val + (2*(int)matImage.at<uchar>(row-2,col+2));
      //Minus 1 Row
      val = val + (4*(int)matImage.at<uchar>(row-1,col-2));
      val = val + (9*(int)matImage.at<uchar>(row-1,col-1));
      val = val + (12*(int)matImage.at<uchar>(row-1,col));
      val = val + (9*(int)matImage.at<uchar>(row-1,col+1));
      val = val + (4*(int)matImage.at<uchar>(row-1,col+2));
      //Current Row
      val = val + (5*(int)matImage.at<uchar>(row,col-2));
      val = val + (12*(int)matImage.at<uchar>(row,col-1));
      val = val + (15*(int)matImage.at<uchar>(row,col));
      val = val + (12*(int)matImage.at<uchar>(row,col+1));
      val = val + (5*(int)matImage.at<uchar>(row,col+2));
      //Plus 1 Row
      val = val + (4*(int)matImage.at<uchar>(row+1,col-2));
      val = val + (9*(int)matImage.at<uchar>(row+1,col-1));
      val = val + (12*(int)matImage.at<uchar>(row+1,col));
      val = val + (9*(int)matImage.at<uchar>(row+1,col+1));
      val = val + (4*(int)matImage.at<uchar>(row+1,col+2));
      //Plus 2 Row
      val = val + (2*(int)matImage.at<uchar>(row+2,col-2));
      val = val + (4*(int)matImage.at<uchar>(row+2,col-1));
      val = val + (5*(int)matImage.at<uchar>(row+2,col));
      val = val + (4*(int)matImage.at<uchar>(row+2,col+1));
      val = val + (2*(int)matImage.at<uchar>(row+2,col+2));
      val2 = (val / 159.0);
      matImage2.at<uchar>(row,col) = (uchar)val2;     
    }
  }  
}

void sobel()
{
  int col, row;
  unsigned int max = 0;
  long valGx, valGy;
  unsigned int val2;
  float angle;
  int newAngle;
  
  for( row = 3; row < height-3; row++ )
  {
    for ( col = 3; col < width-3; col++ )
    {
      valGx = 0;
      valGy = 0;
      val2 = 0;
      //Minus 1 Row
      valGx = valGx + (-matImage2.at<uchar>(row-1,col-1));
      valGx = valGx + (matImage2.at<uchar>(row-1,col+1));
      
      valGy = valGy + (matImage2.at<uchar>(row-1,col-1));
      valGy = valGy + (2*matImage2.at<uchar>(row-1,col));
      valGy = valGy + (matImage2.at<uchar>(row-1,col+1));
      
      //Current Row
      valGx = valGx + (-2*matImage2.at<uchar>(row,col-1));
      valGx = valGx + (2*matImage2.at<uchar>(row,col+1));
      
      //Plus 1 Row
      valGx = valGx + (-matImage2.at<uchar>(row+1,col-1));
      valGx = valGx + (matImage2.at<uchar>(row+1,col+1));
      
      valGy = valGy + (-matImage2.at<uchar>(row+1,col-1));
      valGy = valGy + (-2*matImage2.at<uchar>(row+1,col));
      valGy = valGy + (-matImage2.at<uchar>(row+1,col+1));

      val2 = sqrt((valGx*valGx) + (valGy*valGy));
      matEdgeMagnitude[(row*width)+col] = val2;

      if(val2>max){
	max = val2;
      }
      
      angle = (atan2(valGy,valGx)/3.14159) * 180.0;
      
      //Processing edge directions
      if (((angle < 22.5) && (angle > -22.5)) || (angle > 157.5) || (angle < -157.5))  matEdgeDirection[row,col] = 0;
      if (((angle > 22.5) && (angle < 67.5)) || ( (angle < -112.5) && (angle > -157.5)))   matEdgeDirection[row,col] = 45;
      if (((angle > 67.5) && (angle < 112.5)) || ( (angle < -67.5) && (angle > -112.5)))   matEdgeDirection[row,col] = 90;
      if (((angle > 112.5) && (angle < 157.5)) || ( (angle < -22.5) && (angle > -67.5)))   matEdgeDirection[row,col] = 135;      
    }
  }   
  
  for (int x = 0; x < height; x++) {
	for (int y = 0; y < width; y++) {
	  matImage3.at<uchar>(x,y) = 255.0f * matEdgeMagnitude[(x*width)+y] / max;
	  matEdgeMagnitude[(x*width)+y] = 255.0f * matEdgeMagnitude[(x*width)+y] / max;
	}
  }    
}

void nonMax()
{
  float pix1 = 0;
  float pix2 = 0;
  float pix;
  //Fİnding local maxima with edge direction
  for (int x = 1; x < height - 1; x++) {
    for (int y = 1; y < width - 1; y++) {
      if (matEdgeDirection[x * width + y] == 0) {
	pix1 = matEdgeMagnitude[(x + 1) * width + y];
	pix2 = matEdgeMagnitude[(x - 1) * width + y];
      } else if (matEdgeDirection[x * width + y] == 45) {
	pix1 = matEdgeMagnitude[(x + 1) * width + y - 1];
	pix2 = matEdgeMagnitude[(x - 1) * width + y + 1];
      } else if (matEdgeDirection[x * width + y] == 90) {
	pix1 = matEdgeMagnitude[x * width + y - 1];
	pix2 = matEdgeMagnitude[x * width + y + 1];
      } else if (matEdgeDirection[x * width + y] == 135) {
	pix1 = matEdgeMagnitude[(x + 1) * width + y + 1];
	pix2 = matEdgeMagnitude[(x - 1) * width + y - 1];
      }
      pix = matEdgeMagnitude[x * width + y];
      if ((pix >= pix1) && (pix >= pix2)) {
	matImage3.at<uchar>(x,y)=pix;
      } else {
	matImage3.at<uchar>(x,y)=0;
      }
    }
  }

  bool ok = true;
  while (ok) {
    ok = false;
    for (int x = 1; x < height - 1; x++) {
      for (int y = 1; y < width - 1; y++) {
	if (matImage3.at<uchar>(x,y) == 255) {
	  if (matImage3.at<uchar>(x + 1,y) == 128) {
	    ok = true;
	    matImage3.at<uchar>(x + 1 ,y) = 255;
	  }
	  if (matImage3.at<uchar>(x - 1,y) == 128) {
	    ok = true;
	    matImage3.at<uchar>(x - 1 ,y) = 255;
	  }
	  if (matImage3.at<uchar>(x ,y + 1) == 128) {
	    ok = true;
	    matImage3.at<uchar>(x ,y + 1) = 255;
	  }
	  if (matImage3.at<uchar>(x ,y - 1) == 128) {
	    ok = true;
	    matImage3.at<uchar>(x ,y - 1) = 255;
	  }
	  if (matImage3.at<uchar>(x + 1,y + 1)  == 128) {
	    ok = true;
	    matImage3.at<uchar>(x + 1,y + 1) = 255;
	  }
	  if (matImage3.at<uchar>(x - 1,y - 1)  == 128) {
	    ok = true;
	    matImage3.at<uchar>(x - 1,y - 1) = 255;
	  }
	  if (matImage3.at<uchar>(x - 1,y + 1)  == 128) {
	    ok = true;
	    matImage3.at<uchar>(x - 1,y + 1) = 255;
	  }
	  if (matImage3.at<uchar>(x + 1,y - 1)  == 128) {
	    ok = true;
	    matImage3.at<uchar>(x + 1,y - 1) = 255;
	  }
	}
      }
  }
  if (ok) 
  {
    for (int x = height - 2; x > 0; x--) {
      for (int y = width - 2; y > 0; y--) {
	if (matImage3.at<uchar>(x ,y ) == 255) {
	  if (matImage3.at<uchar>(x + 1,y) == 128) {
	    ok = true;
	    matImage3.at<uchar>(x + 1,y) = 255;
	  }
	  if (matImage3.at<uchar>(x - 1,y) == 128) {
	    ok = true;
	    matImage3.at<uchar>(x - 1,y) = 255;
	  }
	  if (matImage3.at<uchar>(x ,y + 1) == 128) {
	    ok = true;
	    matImage3.at<uchar>(x ,y + 1) = 255;
	  }
	  if (matImage3.at<uchar>(x ,y - 1) == 128) {
	    ok = true;
	    matImage3.at<uchar>(x ,y - 1) = 255;
	  }
	  if (matImage3.at<uchar>(x + 1,y + 1) == 128) {
	    ok = true;
	    matImage3.at<uchar>(x + 1,y + 1) = 255;
	  }
	  if (matImage3.at<uchar>(x - 1,y - 1) == 128) {
	    ok = true;
	    matImage3.at<uchar>(x - 1,y - 1) = 255;
	  }
	  if (matImage3.at<uchar>(x - 1,y + 1) == 128) {
	    ok = true;
	    matImage3.at<uchar>(x - 1,y + 1) = 255;
	  }
	  if (matImage3.at<uchar>(x + 1,y - 1) == 128) {
	    ok = true;
	    matImage3.at<uchar>(x + 1,y - 1) = 255;
	  }
	}
      }
    }
  }
  }

  // Suppression
  for (int x = 0; x < height; x++) {
	  for (int y = 0; y < width; y++) {
		  if (matImage3.at<uchar>(x ,y ) == 128) {
			  matImage3.at<uchar>(x ,y) = 0;
		  }
	  }
  }  
}


//Recursion part of hysteresis
void hysteresisR(int parx, int pary, uchar Threshold)
{
  //Recursively calling adjacent pixels for hysteresis
  uchar currValue = 0;
  for (int x = parx - 1; x <= parx + 1; x++) {
    for (int y = pary - 1; y <= pary + 1; y++) {
      if ((x < height) & (y < width) & (x >= 0) & (y >= 0) & (x != parx) & (y != pary)) {
	currValue = matImage3.at<uchar>(x,y);
	if (currValue != 255) {
	  if (Threshold <= currValue) {
	    matImage3.at<uchar>(x,y) = 255;
	    hysteresisR(x, y, Threshold);
	  }
	  else {
	    matImage3.at<uchar>(x,y) = 0;
	  }
	}
      }
    }
  }
}

//hysteresis method
void hysteresis(uchar highThreshold, uchar lowThreshold)
{
  //Setting pixels bigger than threshold to max
  for (int x = 0; x < height; x++) {
    for (int y = 0; y < width; y++) {
      if (matImage3.at<uchar>(x ,y) >= highThreshold) {
	matImage3.at<uchar>(x,y) = 255;
	hysteresisR(x, y, lowThreshold);
      }
    }
}

  //Setting pixels less than threshold to max
  for (int x = 0; x < height; x++) {
    for (int y = 0; y < width; y++) {
      if (matImage3.at<uchar>(x,y) != 255) {
	      matImage3.at<uchar>(x,y) = 0;
      }
    }
  }  
}

int main( int argc, char** argv )
{
  string inputFileName;
  
  //load image given in parameter
  inputImage = cvLoadImage( argv[1]);
  inputFileName = argv[1];
  
  //Setting width and height of image onto global variables
  width = inputImage->width;
  height = inputImage->height;
  
  // Edge Arrays
  matEdgeMagnitude = new float[width * height];
  matEdgeDirection = new uchar[width * height];

  //Initialize matrixes
  zeroMatrices();

  matImage.create(height,width,  CV_8UC1);
  lumnosity();
  imwrite("gray_"+inputFileName, matImage);  

  matImage2 = matImage.clone();
  matImage3 = matImage.clone();
  
  //Gaussian Blur
  blur();
 
  matImage2.copyTo(matImage);

  //save blurred image
  imwrite("blur_"+inputFileName, matImage2);

  //Sobel Filter
  sobel();
 
  matImage2.copyTo(matImage);

  //save sobel image
  imwrite("sobel_"+inputFileName, matImage3);
  
  nonMax();
  //save nonMax image
  imwrite("nonmax_"+inputFileName, matImage3);
      
  //Hysteresis 
  hysteresis(20,10);
  imwrite("hysteresis_"+inputFileName, matImage3);
  
  
  return 0;

}