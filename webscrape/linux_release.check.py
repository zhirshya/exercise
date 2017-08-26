#!/usr/bin/python -tt
# -*- coding: utf-8 -*-

from lxml import html
import requests

page = requests.get('https://www.kali.org/downloads/')
tree = html.fromstring(page.content)

#todo
#better parse the whole table and find x86-64 arch's version info!

kali_architecture = tree.xpath('/html/body/div[1]/div[2]/div/main/section/div/div/div/div/div/div[5]/div/div/div/div/div/table/tbody/tr[2]/td[1]/text()')
kali_release_version = tree.xpath('/html/body/div[1]/div[2]/div/main/section/div/div/div/div/div/div[5]/div/div/div/div/div/table/tbody/tr[2]/td[4]/strong/text()')
sha256sum = tree.xpath('/html/body/div[1]/div[2]/div/main/section/div/div/div/div/div/div[5]/div/div/div/div/div/table/tbody/tr[2]/td[5]/text()')
#selector
#body > div.l-canvas.sidebar_right.type_wide.titlebar_default > div.l-main > div > main > section > div > div > div > div > div > div:nth-child(5) > div > div > div > div > div > table > tbody > tr:nth-child(2) > td:nth-child(4) > strong

print 'kali_architecture',kali_architecture
print 'kali_release_version:',kali_release_version
print 'sha256sum:',sha256sum
