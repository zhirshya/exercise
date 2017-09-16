#!/usr/bin/python -tt
# -*- coding: utf-8 -*-

import sys
from math import floor
from functools import reduce

def calcTaxed(lst):
#tax 8%
    #sum(float(item) for item in lst)
    total8_sum = sum(float(item) for item in lst)*1.08
    total8_reduce = reduce((lambda x,y: float(x)+float(y)),lst)*1.08
#tax 8% floor
    total8flr_sum = floor(total8_sum)
    total8flr_reduce = floor(total8_reduce)

#tax 10%
    total10_sum = sum(float(item) for item in lst)*1.1
    total10_reduce = reduce((lambda x,y: float(x)+float(y)),lst)*1.1
#tax 10% floor
    total10flr_sum = floor(total10_sum)
    total10flr_reduce = floor(total10_reduce)

#tax 12%
    total12_sum = sum(float(item) for item in lst)*1.12
    total12_reduce = reduce((lambda x,y: float(x)+float(y)),lst)*1.12
#tax 12% floor
    total12flr_sum = floor(total12_sum)
    total12flr_reduce = floor(total12_reduce)

    feeDict = {'8%':{'from func "sum"':total8_sum, 'from func "reduce"':total8_reduce}, '8% floor':{'from func "sum"':total8flr_sum, 'from func "reduce"':total8flr_reduce}, '10%':{'from func "sum"':total10_sum, 'from func "reduce"':total10_reduce}, '10% floor':{'from func "sum"':total10flr_sum, 'from func "reduce"':total10flr_reduce}, '12%':{'from func "sum"':total12_sum, 'from func "reduce"':total12_reduce}, '12% floor':{'from func "sum"':total12flr_sum, 'from func "reduce"':total12flr_reduce}}

    for k,v in list(feeDict.items()):
        print('{0}+{1}:{2}'.format(lst,k,v))

if __name__=='__main__':
    argCnt = len(sys.argv) -1 
    print('len(sys.argv) xcl. of filename:{}'.format(argCnt))
    if not argCnt:
        print('ERROR: specify at least one number!')
        sys.exit(-1)
    else:
        print('arguments to work on:{}'.format(sys.argv[1:]))
        calcTaxed(sys.argv[1:])
