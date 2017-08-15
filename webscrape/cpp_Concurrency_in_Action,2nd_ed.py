#!/usr/bin/python -tt
# -*- coding: utf-8 -*-

from lxml import html
import requests

page = requests.get('https://www.manning.com/books/c-plus-plus-concurrency-in-action-second-edition')
tree = html.fromstring(page.content)

isbn = tree.xpath('//*[@id="top"]/div[1]/div/div/div/div[1]/div/div[2]/div/ul[2]/li[1]/text()')

#6 of 11 chapters available
progress_text = tree.xpath('//*[@id="top"]/div[1]/div/div/div/div[3]/div/div[3]/div[1]/div[3]/p/text()')

#Publication in Fall 2017 (estimated)
publish_when = tree.xpath('//*[@id="top"]/div[1]/div/div/div/div[1]/div/div[2]/div/ul[1]/li[2]/text()')

#todo
#progress bar how-to?
#//*[@id="top"]/div[1]/div/div/div/div[3]/div/div[3]/div[1]/div[3]/div/span[10]

print 'ISBN:',isbn
print 'progress_text:',progress_text
print 'publish_when:',publish_when
