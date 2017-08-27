#!/usr/bin/python -tt
# -*- coding: utf-8 -*-

from lxml import html
import requests

osList = []
osinfo = {'arch':'', 'ver':'', 'url':'', 'sha256sum':''}

#kali
page = requests.get('https://www.kali.org/downloads/')
tree = html.fromstring(page.content)

#todo
#better parse the whole table and find x86-64 arch's version info!

#selector
#body > div.l-canvas.sidebar_right.type_wide.titlebar_default > div.l-main > div > main > section > div > div > div > div > div > div:nth-child(5) > div > div > div > div > div > table > tbody > tr:nth-child(2) > td:nth-child(4) > strong

raw_html_content = tree.xpath('/html/body/div[1]/div[2]/div/main/section/div/div/div/div/div/div[5]/div/div/div/div/div/table/tbody/tr[2]/td[1]/text()')
for field in raw_html_content:
    field = field.strip().replace('\n','').replace('\r','')
    if field:
        osinfo['arch'] = field

raw_html_content = tree.xpath('/html/body/div[1]/div[2]/div/main/section/div/div/div/div/div/div[5]/div/div/div/div/div/table/tbody/tr[2]/td[4]/strong/text()')
for field in raw_html_content:
    field = field.strip().replace('\n','').replace('\r','')
    if field:
        osinfo['ver'] = field

raw_html_content = tree.xpath('/html/body/div[1]/div[2]/div/main/section/div/div/div/div/div/div[5]/div/div/div/div/div/table/tbody/tr[2]/td[5]/text()')
for field in raw_html_content:
    field = field.strip().replace('\n','').replace('\r','')
    if field:
        osinfo['sha256sum'] = field

osList.append(osinfo.copy())

#linux mint

#knoppix

#print 'kali_architecture',kali_architecture
#print 'kali_release_version:',kali_release_version
#print 'sha256sum:',sha256sum
for osItem in osList:
    print osItem

