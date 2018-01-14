#!/usr/bin/python -tt
# -*- coding: utf-8 -*-
'''
如果把ItChat.py文件中的ROBOT改为False，该程序也可以当命令行的微信聊天软件使用
https://github.com/littlecodersh/ItChat/wiki
'''

# todo: get contact card of a contact and forward

import itchat
#import repr

from itchat.content import *
from kitchen.text.converters import getwriter, to_bytes, to_unicode
from kitchen.i18n import get_translation_object

@itchat.msg_register(itchat.content.TEXT)
def print_content(msg):
    print(msg['Text'])

@itchat.msg_register([PICTURE, RECORDING, ATTACHMENT, VIDEO])
def download_files(msg):
    msg.download(msg.fileName)
    typeSymbol = {
        PICTURE: 'img',
        VIDEO: 'vid', }.get(msg.type, 'fil')
    return '@%s@%s' % (typeSymbol, msg.fileName)

itchat.auto_login(hotReload=True, enableCmdQR=2)  # need to zoom out:Ctrl-
#itchat.auto_login(hotReload=True, enableCmdQR=True)  # QR distort

#print "def get_contact(self, update=True):"
#print itchat.get_contact(update=True)

#print "def get_friends(self, update=True):"
#print itchat.get_friends(update=True)

#@itchat.msg_register([PICTURE, RECORDING, ATTACHMENT, VIDEO])
#@itchat.msg_register(itchat.content.ATTACHMENT)
#def download_files(msg):
#    msg.download(msg.fileName)
#    typeSymbol = {
#        ATTACHMENT: 'rar'}.get(msg.type, 'fil')
#    return '@%s@%s' % (typeSymbol, msg.fileName)

#@itchat.msg_register([PICTURE, RECORDING, ATTACHMENT, VIDEO])
#def download_files(msg):
#    msg.download(msg.fileName)
#    itchat.send('@%s@%s' % (
#        'img' if msg['Type'] == 'Picture' else 'fil', msg['FileName']),
#        msg['FromUserName'])
#    return '%s received' % msg['Type']
#
#print download_files

contactinfo = itchat.search_friends(name='aduuchin11.8')[0]
#contactinfo.send_msg(u'1.日立 HITACHI 日本製 海外向け マルチ電圧 100-240V 充電式 電動シェーバー 1枚刃 RM-1850UD:')
#contactinfo.send_image(u'/home/r/Downloads/日立 HITACHI 日本製 海外向け マルチ電圧 100-240V 充電式 電動シェーバー 1枚刃 RM-1850UD.jpg')  # NOT work
#contactinfo.send_image('/home/r/Downloads/1.jpg')
#contactinfo.send_msg(u'2.日立 メンズシェーバー レッド RM-T348 R:')
#contactinfo.send_image(u'/home/r/Downloads/日立 メンズシェーバー レッド RM-T348 R.jpg')  # NOT work
#contactinfo.send_image('/home/r/Downloads/2.jpg')
#contactinfo.send_msg(u'3.日立 メンズシェーバー 往復式 3枚刃 ブラック RM-T305 B:')
#contactinfo.send_image(u'/home/r/Downloads/日立 メンズシェーバー 往復式 3枚刃 ブラック RM-T305 B.jpg')  # NOT work
#contactinfo.send_image('/home/r/Downloads/3.jpg')
#contactinfo.send_msg(u'4.日立 メンズシェーバー ロータリージーソード チタニウムシルバー RM-LX6D S:')
#contactinfo.send_image(u'/home/r/Downloads/日立 メンズシェーバー ロータリージーソード チタニウムシルバー RM-LX6D S.jpg')  # NOT work
#contactinfo.send_image('/home/r/Downloads/4.jpg')
contactinfo.send_image('/home/r/Downloads/4-1.jpg')
contactinfo.send_image('/home/r/Downloads/4-2.jpg')
contactinfo.send_image('/home/r/Downloads/4-3.jpg')

#contactinfo = itchat.search_friends(name='jrmt_Jirem')[0]
#itchat.download_files('/home/r/Downloads/mglbichig_Delkhii.rar',contactinfo[0]['UserName'])
#download_files('/home/r/Downloads/mglbichig_Delkhii.rar',contactinfo[0]['UserName'])  #KeyError: 0
#download_files('/home/r/Downloads/mglbichig_Delkhii.rar',contactinfo[0]['UserName'])  #KeyError: 0
#itchat.download_files('/home/r/Downloads/mglbichig_Delkhii.rar',userName='jrmt_Jirem')  #KeyError: 0
#itchat.download('/home/r/Downloads/mglbichig_Delkhii.rar',contactinfo[0]['UserName'])
#msg.download('/home/r/Downloads/mglbichig_Delkhii.rar',contactinfo[0]['UserName'])
#contactinfo.download_files('/home/r/Downloads/mglbichig_Delkhii.rar')
#1
#contactinfo = itchat.search_friends(name=u'ээж')
#itchat.send_video('/home/r/Downloads/av',contactinfo[0]['UserName'])

'''
contactinfo = itchat.search_friends(name=u'ээж')[0]
print(contactinfo)
'''
#contactinfo.send_msg(u'http://hu.58.com/zufang/25920190492612x.shtml?iuType=p_0&PGTID=0d300008-0032-f40e-addb-cc4844dcc41b&ClickID=2')

#contactinfo.send_file('')

#contactinfo.send_image('')

#http://itchat.readthedocs.io/zh/latest/api/
#contactinfo.send_video('/home/r/Downloads/av')  # NOT work
#contactinfo.send_video('/home/r/Downloads/av/Japanese dad smashes son\'s brand new PS4 (Full version).mp4')
#contactinfo.send_video('')

'''
contactinfo = itchat.search_friends(name=u'au79')[0]
print(contactinfo)
contactinfo.send_msg(u'')

contactinfo = itchat.search_friends(name='E.Hulun(8.21)')
print(contactinfo)
itchat.send(u'',contactinfo[0]['UserName'])
contactinfo = itchat.search_friends(name='Anir Zaamed')
print(contactinfo)
itchat.send(u'Anir,結婚おめでとうございます。おふたりで掴んだ幸せの種をこれから大きく育てていってください、おふたりの更なるご活躍をお祈りいたします。',contactinfo[0]['UserName'])
'''

#2
#error!
#@itchat.msg_register(isGroupChat=True)

# Setup gettext driven translations but use the kitchen functions so
# we don't have the mismatched bytes-unicode issues.
translations = get_translation_object('example')
# We use _() for marking strings that we operate on as unicode
# This is pretty much everything
_ = translations.ugettext

'''
chatrooms = itchat.get_chatrooms(update=True)
for chatroom in chatrooms:
#    print _(u'{}\n'.format(to_unicode(chatroom)))
#    print '{}\n'.format(repr(chatroom).encode('utf-8'))
#    print '{}\n'.format(str(chatroom).encode('utf-8'))
    if chatroom.IsOwner == 1 and chatroom.NickName == 'nominnandin':
        itchat.send(u'了解，公园中间的大桥北端，你们看时间决定吧，到达35分钟前在这儿发个消息，我就出门过去',chatroom['UserName'])
        #print '{}\n'.format(repr(chatroom).decode('unicode-escape'))
        #print '{}\n'.format(repr(chatroom).encode('utf-8'))
'''
'''
grpchat = itchat.search_chatrooms(name=u'小学四班')
print(grpchat)
'''

#3
itchat.run(debug=True)
#itchat.run(debug=True, blockThread=False)
