/********   All Required Header Files ********/
#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <sstream>
#include <queue>
#include <deque>
#include <bitset>
#include <iterator>
#include <list>
#include <stack>
#include <map>
#include <set>
#include <functional>
#include <numeric>
#include <utility>
#include <limits>
#include <time.h>
#include <math.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>

/*
https://developer.android.com/training/multiscreen/screendensities
density-independent pixels (dp) 
160dpi; the "baseline" density
px = dp * (dpi / 160)
*/

/*
9-Patch PNG (recommended)
Dimensions
LDPI:
Portrait: 200x320px

MDPI:
Portrait: 320x480px

HDPI:
Portrait: 480x800px

XHDPI:
Portrait: 720px1280px

XXHDPI:
Portrait: 960px1600px

XXXHDPI:
Portrait: 1280px1920px
*/

using namespace std;
int main(int argc, char** argv){
	vector<int> dpis{160,240,320,480,640};
	vector<pair<int,int>> dipPairs{{65,61},{375,104},{31,31} };
	//vector<pair<int,int>> pxPairs;
	vector<pair<double,double>> pxPairs;
	for(int dpi : dpis){
		double dpiBaselineRatio = dpi/160.0;
		for(auto dip : dipPairs){
			pair<double,double> pxPair = {dpiBaselineRatio*dip.first, dpiBaselineRatio*dip.second};
			pxPairs.emplace_back(pxPair);
		}
	}

	for(auto pxPair : pxPairs)
		cout << pxPair.first << '*' << pxPair.second << '\n';

	return 0;
}

