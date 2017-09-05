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

#https://codereview.stackexchange.com/questions/131371/script-to-print-weather-report-from-openweathermap-api

#todo: Possible update of API version? e.g. 3.0, 5.5, 7.5 etc. in http://api.openweathermap.org/data/2.5
#Carbon Monoxide Data: http://openweathermap.org/api/pollution/co

def time_converter(time):
    converted_time = datetime.datetime.fromtimestamp(
        int(time)
    ).strftime('%I:%M %p')
    return converted_time

def url_builder(city_id):
    unit = 'metric'  # For Fahrenheit use imperial, for Celsius use metric, and the default is Kelvin.
    api = 'http://api.openweathermap.org/data/2.5/weather?id='     # Search for your city ID here: http://bulk.openweathermap.org/sample/city.list.json.gz

    full_api_url = api + str(city_id) + '&mode=json&units=' + unit + '&APPID=8ae110110260b06d58fbba48a73cacc0'
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
    cities = {'Hailar':2037078, 'Tokyo':1850144, 'Saint Petersburg':498817, 'Moscow':524901, 'Manzhouli':2035836, 'Harbin':2037013}
    try:
        if isinstance(cities, dict):
            for k,v in list(cities.items()):
                data_output(data_organizer(data_fetch(url_builder(v))))
    except IOError as xcpt:
    #except IOError:
        print(xcpt)
