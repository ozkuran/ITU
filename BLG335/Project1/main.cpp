/********************************************************/
/* 			Fall 2013 ITU BLG 335E Project 1			*/
/*          040000815 Mahmut Ali ÖZKURAN				*/
/*														*/
/*	To Compile 	: make									*/
/*	To Test		: make run								*/
/********************************************************/


#include <time.h>
#include <iostream>
#include <fstream>
#include <string>
#include <math.h>  
#include <stdlib.h> 
#include <vector>

#define toRadian (3.1415926535/180)
#define earthRadius 6371
using namespace std;



class City
{
	public:
		string name;   						// Name of the City
		double latitude;  					// Latitude of the City
		double longitude;   				// Longitude of the City
		double distance;					// Distance of City from selected point
};

// Operator Overloads - Start
// Operator Overloads - Stream - Start

std::ostream& operator << (std::ostream& stream, const City& objCity)
{
	stream << objCity.name << "\t" << objCity.latitude << "\t" << objCity.longitude << "\t" << objCity.distance << endl;
};

// Operator Overloads - Stream - End
// Operator Overloads - Comparison - Start

bool operator < (City const& objCityA, City const& objCityB) 
{
	//cout << objCityA.name << endl;
	return objCityA.distance < objCityB.distance;
};

bool operator > (City const& objCityA, City const& objCityB) 
{
	return objCityA.distance > objCityB.distance;
};

bool operator<=(City const& objCityA, City const& objCityB) 
{
	return !(objCityA.distance > objCityB.distance);
};

bool operator>=(City const& objCityA, City const& objCityB) 
{
	return !(objCityA.distance < objCityB.distance);
};

// Operator Overloads - Comparison - End
// Operator Overloads - End


class Engine{
	private:
		double centerLatitude;
		double centerLongitude;
		double calculateDistance (double, double);
	public:
		Engine();
		Engine(double, double);
		Engine(double, double,int);
		std::vector<City> citiesList;
		int requestedItemCount;
		int loadItems(int);
		void insertionSort(vector<City>&);
		vector<City> merge(vector<City>, vector<City>);
		vector<City> mergeSort(vector<City>);
		vector<City> linearSearch(vector<City>);
		void saveItems();
};


Engine::Engine () // Default Constructor which sets Coordinates of Istanbul
{
	centerLatitude = 41.01;
	centerLongitude = 28.58;
};


Engine::Engine(double parLatitude, double parLongitude, int parItemCount)
{
	centerLatitude = parLatitude;
	centerLongitude = parLongitude;
	loadItems(parItemCount);
};

Engine::Engine(double parLatitude, double parLongitude)
{
	centerLatitude = parLatitude;
	centerLongitude = parLongitude;
};



void Engine::insertionSort(vector<City>& parCitiesList)
{
	int j;
	City val;
	for(int i = 1; i < parCitiesList.size(); i++)
	{
		val = parCitiesList[i];
		j = i - 1;
		while(j >= 0 && parCitiesList[j] > val)
		{
			parCitiesList[j + 1] = parCitiesList[j];
			j = j - 1;
		}
		parCitiesList[j + 1] = val;
	}
};

vector<City> Engine::merge(vector<City> left, vector<City> right)
{
    vector<City> result;
    int ptrLeft = 0, ptrRight = 0;
 
    while(ptrLeft < left.size() && ptrRight < right.size())
    {
        if(left[ptrLeft] < right[ptrRight])
        {
            result.push_back(left[ptrLeft]);
            ptrLeft++;
        }
        else
        {
            result.push_back(right[ptrRight]);
            ptrRight++;
        }
    }
 
    while(ptrLeft < left.size())
    {
        result.push_back(left[ptrLeft]);
        ptrLeft++;
    }
 
    while(ptrRight < right.size())
    {
        result.push_back(right[ptrRight]);
        ptrRight++;
    }
 
    return result;
}


vector<City> Engine::mergeSort(vector<City> parCitiesList)
{
	if (parCitiesList.size() == 1)
	{
      return parCitiesList;
	}
 
	//vector<City> left, right, result;
    std::vector<City>::iterator middle = parCitiesList.begin() + (parCitiesList.size()  / 2);

    vector<City> left(parCitiesList.begin(), middle);
    vector<City> right(middle , parCitiesList.end());
 
    // Perform a merge sort on the two smaller vectors
    left = mergeSort(left);
    right = mergeSort(right);
 
    return merge(left, right);
}

vector<City> Engine::linearSearch(vector<City> parCitiesList)
{
	vector<City> tempList;
	City tempCity; // Temporary array to hold requested items
	int ptrTempList;
	for(int i = 1; i < parCitiesList.size(); i++) // First loop iterating thru input array
	{
		tempCity = parCitiesList[i];
		if (tempList.size() == 0)
		{
			tempList.push_back(tempCity);
		}
		else
		{
			ptrTempList = 0;
			while(tempList[ptrTempList] < tempCity && tempList.size() > ptrTempList) // Second loop iterating thru output array
			{
				ptrTempList++;
			}
			tempList.insert(tempList.begin() + ptrTempList,tempCity);
			if(tempList.size()>requestedItemCount){
				tempList.pop_back();
			}
		}
	}
    return tempList;
}


double Engine::calculateDistance (double latitude, double longitude)
{
    double latitudeDifference = (latitude - centerLatitude) * toRadian;
    double longitudeDifference = (longitude - centerLongitude) * toRadian;

    double Haversine = pow(sin(latitudeDifference/2), 2) + cos(centerLatitude) * cos(centerLatitude) * pow(sin(longitudeDifference/2), 2 );
 
    double arcRadians = 2 * atan2( sqrt(Haversine), sqrt( 1 - Haversine ));
    double distanceInKilometers = earthRadius * arcRadians;

    return distanceInKilometers; // Return distance
}
	
int Engine::loadItems(int itemCount)
{
	int lineNumber = 0;
	string line;
	ifstream myfile("location.txt");
	if(myfile.is_open())
	{
		while (lineNumber < itemCount)
		{
			getline(myfile, line);
			unsigned pos = line.find("\t"); 
			string city = line.substr(0,pos);
			line = line.substr(pos+1);
			pos = line.find("\t");
			double latitude = atof(line.substr(0,pos).c_str());
			line = line.substr(pos+1);
			double longitude = atof(line.substr(0,pos).c_str());
			City newCity;
			newCity.name = city;
			newCity.latitude = latitude;
			newCity.longitude = longitude;
			newCity.distance = calculateDistance(latitude, longitude);
			citiesList.push_back(newCity);
			lineNumber++;
		}
		myfile.close();
	}
	else
	{
		cout << "File could not be opened." << endl; 
		return 0;
	}
}

void Engine::saveItems()
{
	ofstream outputFile;
	outputFile.open("output.txt");
	for(int i = 0; i < requestedItemCount; i++)
	{
		outputFile << citiesList[i]; 
	}
	outputFile.close();
}

int main (int argc, char* argv[]) {
    if (argc < 6) 
	{ 
        std::cout << "Usage is NumberofCitiesWillBeLoaded NumberofClosestCitiessToBeFound algorithmType[I(Insertion Sort),M(Merge Sort),L(Linear Search)] latitude longitude\n"; 
        exit(0);
    } 
	else 
	{
		clock_t t;
		Engine engine(atof(argv[4]),atof(argv[5]));
		engine.loadItems(atoi(argv[1]));
		t = clock();
		if (string(argv[2]) == "N/2")
		{
			engine.requestedItemCount = atoi(argv[1])/2;
		}
		else
		{
			engine.requestedItemCount = atoi(argv[2]);
		}

		if (string(argv[3]) == "I" || string(argv[3]) == "i")
		{
			engine.insertionSort(engine.citiesList);
		}
		else if (string(argv[3]) == "M" || string(argv[3]) == "m")
		{
			engine.citiesList = engine.mergeSort(engine.citiesList);
		}
		else if (string(argv[3]) == "L" || string(argv[3]) == "l")
		{
			engine.citiesList = engine.linearSearch(engine.citiesList);
		}
		else
		{
			cout << argv[3] << " Sorting Algorithm not implemented please use one of three (M)erge, (I)nsertion or (L)inear Search" << endl;
		};
		//return 0;
		t = clock() - t;
		cout << "It took me " << t << " clicks (" << ((float)t)/CLOCKS_PER_SEC << " seconds)." << endl;
		engine.saveItems();
	}
	return 0;

}
