#!/usr/bin/python -tt
# -*- coding: utf-8 -*-
 
from __future__ import print_function
from selenium import webdriver
import sys
import time
 
# grab the url as the first command line argument
url = sys.argv[1]
 
# create a Chrome browser
driver = webdriver.Chrome('/home/r/.local/chromedriver-Linux64')
driver.maximize_window()

# open the url from the command line
driver.get(url)
 
# scroll to the bottom in order to load the comments
driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
time.sleep(3)
 
# wait for the comments to load
while True:
    # if comments load, then break out of the while loop
    try:
        driver.find_element_by_id("comment-section-renderer-items")
        break
    # otherwise, sleep for three seconds and try again
    except:
        time.sleep(3)
        continue
 
# print the comments, separated by a line
for item in driver.find_elements_by_class_name("comment-renderer"):
    print(item.text)
    print("-"*80)
 
# close the browser
driver.close()
