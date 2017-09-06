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
    
    cities = {'Hailar':2037078, 'Tokyo':1850144, 'Saint Petersburg':498817, 'Moscow':524901, 'Manzhouli':2035836, 'Harbin':2037013}
    try:
        if isinstance(cities, dict):
            for k,v in list(cities.items()):
                data_output(data_organizer(data_fetch(url_builder(v,apiKey))))
    except IOError as xcpt:
    #except IOError:
        print(xcpt)
