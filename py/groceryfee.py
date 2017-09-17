#!/usr/bin/python -tt
# -*- coding: utf-8 -*-

from __future__ import print_function
from __future__ import unicode_literals
from __future__ import division
from __future__ import absolute_import
from future import standard_library
standard_library.install_aliases()

import sys
import re
from math import floor
from functools import reduce

def calcTaxed(rawLst):
#todo: duplicate: 88x3 or 88 x 3
#filter out NaN except ① num1[x|*|X]num2 and ② args consists solely of symbols in ['x', '*', 'X']
#for ② multiply previous and following numbers
#③

    multiplySymbols = ['x', '*', 'X']
    numLst = []
    
    pttrn = re.compile('^\d*[x*]+\d+|\d+[x*]+\d*$', re.IGNORECASE)

    for arg in rawLst:
        try:
            numLst.append(float(arg))
        except ValueError as ve:
            print(ve)
            if pttrn.match(arg):
                multiplicandLst = list(arg.split(multiplySymb) for multiplySymb in multiplySymbols if arg.find(multiplySymb) > -1)
                #multiplicandLst = [arg.split(multiplySymb) for multiplySymb in multiplySymbols if arg.find(multiplySymb) > -1]
                print('multiplicandLst before flattening:{}'.format(multiplicandLst))
                multiplicandLstFlat = [item for splitLst in multiplicandLst for item in splitLst]
                print('multiplicandLst after flattening:{}'.format(multiplicandLstFlat))
                
                numLst.append(reduce(lambda x,y: float(x)*float(y), multiplicandLstFlat))
                #numLst.append(reduce((lambda x,y: float(x)*float(y)), multiplicandLst))
            else:
                continue
 
    print('numLst:{}'.format(numLst))
    varSum = sum(item for item in numLst)
    varSumByReduce = reduce(lambda x,y: x+y,numLst)

    #varSum = sum(float(item) for item in numLst)
    #varSumByReduce = reduce((lambda x,y: float(x)+float(y)),numLst)

#tax 8%
    varSum8 = varSum*1.08
    varSumByReduce8 = varSumByReduce*1.08
#tax 8% floor
    varSum8flr = floor(varSum8)
    varSumByReduce8flr = floor(varSumByReduce8)

#tax 10%
    varSum10 = varSum*1.1
    varSumByReduce10 = varSumByReduce*1.1
#tax 10% floor
    varSum10flr = floor(varSum10)
    varSumByReduce10flr = floor(varSumByReduce10)

#tax 12%
    varSum12 = varSum*1.12
    varSumByReduce12 = varSumByReduce*1.12
#tax 12% floor
    varSum12flr = floor(varSum12)
    varSumByReduce12flr = floor(varSumByReduce12)

    feeDict = {'8%':{'from func "sum"':varSum8, 'from func "reduce"':varSumByReduce8}, '8% floor':{'from func "sum"':varSum8flr, 'from func "reduce"':varSumByReduce8flr}, '10%':{'from func "sum"':varSum10, 'from func "reduce"':varSumByReduce10}, '10% floor':{'from func "sum"':varSum10flr, 'from func "reduce"':varSumByReduce10flr}, '12%':{'from func "sum"':varSum12, 'from func "reduce"':varSumByReduce12}, '12% floor':{'from func "sum"':varSum12flr, 'from func "reduce"':varSumByReduce12flr}}

    for k,v in list(feeDict.items()):
        print('{0}+{1}:{2}'.format(rawLst,k,v))

if __name__=='__main__':
    argCnt = len(sys.argv) -1 
    print('len(sys.argv) xcl. of filename:{}'.format(argCnt))
    if not argCnt:
        print('ERROR: specify at least one number!')
        sys.exit(-1)
    else:
        print('arguments to work on:{}'.format(sys.argv[1:]))
        calcTaxed(sys.argv[1:])
