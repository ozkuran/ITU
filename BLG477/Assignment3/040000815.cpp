#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/opencv.hpp"
#include <iostream>
#include <stdio.h>
#include <stdlib.h> 
#include <string> 
#include <algorithm>
#include <vector>
#include <iterator>
#include <fstream>

using namespace std;
using namespace cv;

bool debug = false; // Debug Switch
bool videoGiven = false;


class Engine
{
  public:
    Engine();
    Engine(int);
    Engine(string);
    ~Engine();
    void Process();
  private:
    int inputType;
    double width;
    double height;
    double frameNumber;
    int Histogram1[255];
    int Histogram2[255];
    int truthTotal, pixTotal, pixHit, histHit, histTotal, edgeHit, edgeTotal;
    Mat Frame1, Frame2, Frame3;
    string inputFile;
    vector<int> truth;
    VideoCapture cap; 			// Video Capture Object
    void Initialize();			// Initialization Routine
    bool LoadVideoFile();		// Loads Video File onto VideoCapture Objects
    void CopyHistogram();		// Copies Newly Created Histogram onto older one
    double CalculateHistogramDifference();	//Calculates Histogram Difference
    void CalculateHistogram();		// Calculates Histogram
    void PrintHistogram();		// DEBUG ROUTINE - Prints Histogram
    void ProcessVideo(); 		// Process Videos
    void ProcessVideoFrames();		// Process Loaded Video Frames
    void ProcessImageSequence();	//Loads list of Files onto vector
    bool PixelDifference();		//Pixel Difference Routine
    bool EdgeChangeRatio();		//Edge Change Ratio Routine
};

Engine::Engine()
{
  this->inputType = 0;
  Initialize();
};

Engine::Engine(int inputT)
{
  this->inputType = inputT;
  Initialize();
};

Engine::Engine(string filename)
{
  this->inputType = 1;
  this->inputFile = filename;
  Initialize();
};

Engine::~Engine()
{
};

void Engine::Process()
{
  Initialize();
  if(this->inputType == 1)
  {	
    ProcessVideo();
  }
  else
  {
    ProcessImageSequence();
  }
};

void Engine::ProcessVideo()
{
  LoadVideoFile();
  ProcessVideoFrames();
};

void Engine::ProcessVideoFrames()
{
  for(;;)
  {
    frameNumber++;
    Mat tmpFrame;
    cap.retrieve(tmpFrame);
    bool readSuccess = cap.read(tmpFrame);
    if(readSuccess){
      cvtColor(tmpFrame, Frame1, CV_BGR2GRAY);
    } else {
	break;
    }
    CopyHistogram();
    CalculateHistogram();
    double HD = CalculateHistogramDifference();
    //cout << HD << endl;
    if (HD > 0.5)
    {
      cout << "Frame Number = " << frameNumber << endl;
      if (debug) PrintHistogram();
    }
  }  
};

void Engine::Initialize()
{
  for(int i = 0; i<255; i++){
    Histogram1[i] = 0;
    Histogram2[i] = 0;
  }  
  frameNumber = 0;
  Frame1 = Mat::zeros(1,1,CV_BGR2GRAY);
  Frame2 = Mat::zeros(1,1,CV_BGR2GRAY);
  Frame3 = Mat::zeros(1,1,CV_BGR2GRAY);
  ifstream fTruth;
  string strLine;
  fTruth.open("gr_truth.txt"); 
  if (fTruth.is_open())
  {
    while (getline(fTruth,strLine) )
    {
      truth.push_back(atoi(strLine.c_str()));
    }
    fTruth.close();
  }
  truthTotal = truth.size() / 2;
  pixTotal = 0;
  pixHit = 0;
  histHit = 0;
  histTotal = 0;
  edgeHit = 0; 
  edgeTotal = 0;
}


bool Engine::LoadVideoFile()
{
  this->cap.open(this->inputFile);
  if (!cap.isOpened())  // If video not loaded exit the program
  {
    cout << "Cannot open the video file" << endl;
    return false;
  }
  else 
  {
    this->width = cap.get(CV_CAP_PROP_FRAME_WIDTH); 
    this->height = cap.get(CV_CAP_PROP_FRAME_HEIGHT); 
    return true;
  }
}

void Engine::CopyHistogram()
{
  for(int i; i<255; i++){
    Histogram2[i] = Histogram1[i];
    Histogram1[i] = 0;
  }
}

double Engine::CalculateHistogramDifference()
{
  double total, average;
  total = 0;
  for(int i = 0; i<255; i++){
    total = total + abs(Histogram1[i] - Histogram2[i]);
  }  
  if ((debug) && (total > 0)) 
  {
    cout << total << endl;
  }
  average = total / (width * height);
  return average;
}

void Engine::CalculateHistogram()
{
    for(int i=0;i<Frame1.rows;i++){
      for(int j=0;j<Frame1.cols;j++){
	Histogram1[Frame1.at<uchar>(i,j)] = Histogram1[Frame1.at<uchar>(i,j)] + 1;
      }
    }
}

void Engine::PrintHistogram()
{
  for(int i= 0; i<255; i++){
    cout << " " << i << "= " << Histogram1[i] << " ";
  }  
  cout << endl;
};

bool Engine::PixelDifference()
{
  double total;
  total = 0;
  for(int i=0;i<Frame1.rows;i++){
    for(int j=0;j<Frame1.cols;j++){
      total = total + abs(Frame1.at<uchar>(i,j) - Frame2.at<uchar>(i,j));
    }
  }  
  double average = total / (width * height);
  if (average > 12)
  {
    return true;
  }
  else 
  {
    return false;
  }
}

bool Engine::EdgeChangeRatio()
{
  Canny(Frame1,Frame1,75,150);
  double total;
  total = 0;
  for(int i=0;i<Frame1.rows;i++){
    for(int j=0;j<Frame1.cols;j++){
      total = total + (Frame1.at<uchar>(i,j) - Frame3.at<uchar>(i,j));
    }
  }  
  double average = total / (width * height * 255);
  
  if (average > 0.004)
  {
    return true;
  }
  else 
  {
    return false;
  }  
}

void Engine::ProcessImageSequence()
{
  ostringstream tmpStr;
  ofstream fPixel, fHistogram, fEdge, fResult;
  fPixel.open("pixel_diff.txt");
  fHistogram.open("hist_diff.txt");
  fEdge.open("ec_ratio.txt");
  fResult.open("result.txt");
  for(;;)
  {
    frameNumber++;
    Mat tmpFrame;
    tmpStr.str("");
    tmpStr << "./video_seq/" << frameNumber << ".jpg";
    tmpFrame = imread(tmpStr.str(), CV_LOAD_IMAGE_COLOR);   // Read the file
    if (debug) {
      cout << tmpStr.str() << endl;
    }
    if(!tmpFrame.data)                              // Check for invalid input
    {
      break;
    }
    else
    {
      cvtColor(tmpFrame, Frame1, CV_BGR2GRAY);
      height = Frame1.rows;
      width = Frame1.cols;
    }
    
    CopyHistogram();
    CalculateHistogram();
    double HD = CalculateHistogramDifference();
    //cout << HD << endl;
    if ((HD > 0.2) && (frameNumber != 1))
    {
      cout << "Histogram Difference Frame Number = " << frameNumber-1 << endl;
      fHistogram << frameNumber-1 << endl;
      if (debug) PrintHistogram();
      std::vector<int>::const_iterator it = std::find(truth.begin(), truth.end(), frameNumber-1);
      histTotal++;
      if (it != truth.end())
      {
	histHit++;
      }
    }
    bool PD = PixelDifference();
    if(PD && (frameNumber != 1))    
    {
      cout << "Pixel Difference Frame Number = " << frameNumber-1 << endl;
      fPixel << frameNumber-1 << endl;
      std::vector<int>::const_iterator it = std::find(truth.begin(), truth.end(), frameNumber-1);
      pixTotal++;
      if (it != truth.end())
      {
	pixHit++;
      }      
    };
    Frame1.copyTo(Frame2);
    
    bool ECD = EdgeChangeRatio();
    if(ECD && (frameNumber != 1))    
    {
      cout << "Edge Change Ratio Frame Number = " << frameNumber-1 << endl;
      fEdge << frameNumber-1 << endl;
      std::vector<int>::const_iterator it = std::find(truth.begin(), truth.end(), frameNumber-1);
      edgeTotal++;
      if (it != truth.end())
      {
	edgeHit++;
      }
    };
    Frame1.copyTo(Frame3);    
  }  
  fResult << "Technique		\t\t	Precission 	\t\t	Recall\t\t" << endl;
  fResult << "Pixel		\t\t	"<< pixHit << "/" << pixTotal <<"\t\t	"<< pixHit << "/" << truthTotal <<"\t\t" << endl;
  fResult << "Histogram		\t\t	"<< histHit << "/" << histTotal <<"\t\t	"<< histHit << "/" << truthTotal <<"\t\t" << endl;
  fResult << "Edge		\t\t	"<< edgeHit << "/" << edgeTotal <<"\t\t	"<< edgeHit << "/" << truthTotal <<"\t\t" << endl;
  fResult.close();
  fPixel.close();
  fHistogram.close();
  fEdge.close();
};

int main(int argc, char* argv[])
{
  //Engine eng("HIMYM.avi");
  Engine eng(0);
  eng.Process();
  return 0;
}