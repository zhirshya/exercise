#https://www.youtube.com/watch?v=eaYX0Ee0Kcg
#K Closest Points to the Origin

#include <vector>
#include <map>
#include <cmath>

using namespace std;

//Time complexity: O(n*logn), Space complexity: O(n);
vector<pair<int, int>> kClosestPoints(const vector<pair<int, int>>& points, size_t k) {

     map<size_t, vector<int>> distanceToIndex;  //maight be more than one point with the same distance. 
     vector < pair<int, int>> result;
     map<size_t, vector<int>>::const_iterator map_it;
     vector<int>::const_iterator vec_it;

     //Create a map of sorted distances refereing to the indecies of the corresponding points in the input vector.
     //Time complexity: Inserting n points. Each insertion is logn ==> O(n*logn)
     for (size_t i = 0; i < points.size(); ++i) {
         size_t d = pow(points[i].first, 2) + pow(points[i].second, 2); //No need to calc the root. Can overflow!
         distanceToIndex[d].push_back(i); 
     }

    //This will run in O(k) time complexity.
    for (map_it = distanceToIndex.begin(); map_it != distanceToIndex.end(); ++map_it) {
        for (vec_it = map_it->second.begin(); vec_it != map_it->second.end(); ++vec_it) {
             if (result.size() == k) break;
             result.push_back(points[*vec_it]);
        }
        if (result.size() == k) break;
    }
    return result;
}

