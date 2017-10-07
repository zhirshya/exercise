#!/usr/bin/python -tt
# -*- coding: utf-8 -*-

from __future__ import print_function
from __future__ import unicode_literals
from __future__ import division
from __future__ import absolute_import
from builtins import dict
from builtins import int
from future import standard_library
standard_library.install_aliases()
from builtins import str
import datetime
import json
import urllib.request
import argparse
import sys

#https://codereview.stackexchange.com/questions/131371/script-to-print-weather-report-from-openweathermap-api

#todo: Possible update of API version? e.g. 3.0, 5.5, 7.5 etc. in http://api.openweathermap.org/data/2.5
#Carbon Monoxide Data: http://openweathermap.org/api/pollution/co

def time_converter(time):
    converted_time = datetime.datetime.fromtimestamp(
        int(time)
    ).strftime('%I:%M %p')
    return converted_time

def url_builder(city_id, api_key):
    unit = 'metric'  # For Fahrenheit use imperial, for Celsius use metric, and the default is Kelvin.
    api = 'http://api.openweathermap.org/data/2.5/weather?id='  # Search for your city ID here: http://bulk.openweathermap.org/sample/city.list.json.gz
    full_api_url = api + str(city_id) + '&mode=json&units=' + unit + '&APPID=' + api_key
    return full_api_url

def data_fetch(full_api_url):
    with urllib.request.urlopen(full_api_url) as url:
        return json.loads(url.read().decode('utf-8'))
        #output = url.read().decode('utf-8')
        #raw_api_dict = json.loads(output)
        #return raw_api_dict

def data_organizer(raw_api_dict):
    data = dict(
        city=raw_api_dict.get('name'),
        country=raw_api_dict.get('sys').get('country'),
        temp=raw_api_dict.get('main').get('temp'),
        temp_max=raw_api_dict.get('main').get('temp_max'),
        temp_min=raw_api_dict.get('main').get('temp_min'),
        humidity=raw_api_dict.get('main').get('humidity'),
        pressure=raw_api_dict.get('main').get('pressure'),
        sky=raw_api_dict['weather'][0]['main'],
        sunrise=time_converter(raw_api_dict.get('sys').get('sunrise')),
        sunset=time_converter(raw_api_dict.get('sys').get('sunset')),
        wind=raw_api_dict.get('wind').get('speed'),
        wind_deg=raw_api_dict.get('deg'),
        dt=time_converter(raw_api_dict.get('dt')),
        cloudiness=raw_api_dict.get('clouds').get('all')
    )
    return data

def data_output(data):
    data['m_symbol'] = '\xb0' + 'C'
#    m_symbol = '\xb0' + 'C'
#    print('---------------------------------------')
#    print('Current weather in: {}, {}:'.format(data['city'], data['country']))
#    print(data['temp'], m_symbol, data['sky'])
#    print('Max: {}, Min: {}'.format(data['temp_max'], data['temp_min']))
#    print('')
#    print('Wind Speed: {}, Degree: {}'.format(data['wind'], data['wind_deg']))
#    print('Humidity: {}'.format(data['humidity']))
#    print('Cloud: {}'.format(data['cloudiness']))
#    print('Pressure: {}'.format(data['pressure']))
#    print('Sunrise at: {}'.format(data['sunrise']))
#    print('Sunset at: {}'.format(data['sunset']))
#    print('')
#    print('Last update from the server: {}'.format(data['dt']))
#    print('---------------------------------------')
    s = '''---------------------------------------
Current weather in: {city}, {country}:
{temp}{m_symbol} {sky}
Max: {temp_max}, Min: {temp_min}

Wind Speed: {wind}, Degree: {wind_deg}
Humidity: {humidity}
Cloud: {cloudiness}
Pressure: {pressure}
Sunrise at: {sunrise}
Sunset at: {sunset}

Last update from the server: {dt}
---------------------------------------'''
    print(s.format(**data))

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Fetch weather data for designated cities using OpenWeatherMap API Key(ASCII alphanumeric) specified on command line.', add_help=True)
    parser.add_argument('--apikey', required=True, action='store', dest='api_key', help='take in OpenWeatherMap API Key(ASCII alphanumeric)')
    parser.add_argument('--version', action='version', version='%(prog)s 1.0')

    print('parser.parse_args():{0}'.format(parser.parse_args()))
    args = parser.parse_args()

    print('sys.argv[0]:{0}'.format(sys.argv[0]))
    print('sys.argv[1]:{0}'.format(sys.argv[1]))
    
    apiKey = args.api_key
    print('(local var)apiKey:{0}'.format(apiKey))
    
    #49°12'00.0"N 119°42'00.0"E
    #DMS	49° 12′ 0″ N, 119° 42′ 0″ E
    #Decimal    49.2, 119.7	
    #Geo URI	geo:49.2,119.7
    #UTM	50U 696682 5453199

    #51°50′00″ с. ш. 107°37′00″ в. д. HGЯO
    #Geo URI	geo:51.833333,107.6

    #Geo URI	geo:55.75,37.616667
    #Geo URI	geo:59.95,30.3
    #Geo URI	geo:52.283333,104.283333

    #Geo URI	geo:45.75,126.633333
    #Geo URI	geo:49.6,117.433333

#URL format http://api.openweathermap.org/pollution/v1/co/{location}/{datetime}.json?appid={api_key}
#http://api.openweathermap.org/pollution/v1/co/0.000,10.000/2016-03-01Z.json?appid={your-api-key} 3 digits (78m)

    cities = {2037078:['Hailar',49.200000,119.700000], 2036892:['Hohhot',40.816667,111.65], 2014407:['Улан-Удэ',51.833333,107.6], 2023469:['Irkutsk',52.283333,104.283333], 1850144:['Tokyo',35.683333,139.683333], 498817:['Saint Petersburg',59.95,30.3], 524901:['Moscow',55.75,37.616667], 2035836:['Manchur',49.6,117.433333], 2037013:['Harbin',45.75,126.633333]}
    try:
        if isinstance(cities, dict):
            for k,v in list(cities.items()):
                print("k:{}".format(k))
                print("latitude:{}".format(v[1]))
                print("longitude:{}".format(v[2]))
                data_output(data_organizer(data_fetch(url_builder(k,apiKey))))
                print("{0}{1}".format("Access Carbon Monoxide index for any location on Earth! Data is available in JSON.",'''
                    Air pollution (beta):
                        Carbon Monoxide Data (CO)
                        Ozone Data (O3)
                        Sulfur Dioxide Data (SO2)
                        Nitrogen Dioxide Data (NO2)'''))
                #HTTP Error 404: Not Found
                print(data_fetch("{0}{1},{2}{3}{4}".format("http://api.openweathermap.org/pollution/v1/co/",v[1],v[2],"/current.json?appid=",apiKey)))
    except IOError as xcpt:
    #except IOError:
        print(xcpt)
