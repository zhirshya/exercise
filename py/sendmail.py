#!/usr/bin/python -tt
# -*- coding: utf-8 -*-

from __future__ import print_function
from __future__ import unicode_literals
from __future__ import division
from __future__ import absolute_import
from builtins import open
from future import standard_library
standard_library.install_aliases()
import sys
import os
import string
import smtplib
import configparser
import argparse
import codecs
# For guessing MIME type based on file name extension
import mimetypes
#from email.message import EmailMessage
#from email.policy import SMTP
from email.mime.base import MIMEBase
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email import Encoders
from email.header import Header
from email.Utils import formatdate
from email import Charset
from email.generator import Generator
from io import StringIO

#smtp-mail.outlook.com:587 ⇒ for res in getaddrinfo(host, port, 0, SOCK_STREAM): socket.gaierror: [Errno -2] Name or service not known
#smtp-mail.outlook.com:25 ⇒ for res in getaddrinfo(host, port, 0, SOCK_STREAM): socket.gaierror: [Errno -2] Name or service not known
#smtp.gmail.com:25 ⇒ File "/usr/lib64/python2.7/socket.py", line 575, in create_connection: raise err: socket.error: [Errno 101] Network is unreachable
#smtp.gmail.com:587 ⇒ OK
#20171022 170722.683753052+09:00:00JST Sunday

#todo: reset vim tabwidth to 8 or default and use default tabwidth to indent!

def send_email(mailInfoFile,cnfGroup):
#    if os.path.exists(mailInfoFile):  #'infile', type=argparse.FileType('r'), default=sys.stdin,... check for validity in the beginning?
    if os.path.exists(mailInfoFile):
        config = configparser.ConfigParser()
        config.read(mailInfoFile, encoding='utf-8')

        from_emails = config[cnfGroup]['from']
        to_emails = config[cnfGroup]['to']
        cc_emails = config[cnfGroup]['cc']
        bcc_emails = config[cnfGroup]['bcc']
        subject = config[cnfGroup]['subject']
        body = config[cnfGroup]['body']
        attachments = config[cnfGroup]['attachment']
        passwd = config[cnfGroup]['passwd']
        smtpServer = config[cnfGroup]['smtpServer']
        port = config[cnfGroup]['port']

        print('(trace):from_emails:[', from_emails, ']')
        print('(trace):to_emails:[', to_emails, ']')
        print('(trace):cc_emails:[', cc_emails, ']')
        print('(trace):bcc_emails:[', bcc_emails, ']')
        print('(trace):subject:[', subject.encode('utf-8'), ']')
        print('(trace):body:[', body.encode('utf-8'), ']')
        print('(trace):attachments:[', attachments.encode('utf-8'), ']')
    #        print('(trace):body:[', repr(body), ']')
    #        print('(trace):attachments:[', repr(attachments), ']')
        print('(trace):passwd:[', passwd, ']')
        print('(trace):smtpServer:[', smtpServer, ']')
        print('(trace):port:[', port, ']')

        msg = MIMEMultipart()
    #        msg = EmailMessage()  #python3
        msg['From'] = from_emails
        msg['To'] = to_emails
        msg['cc'] = cc_emails
        msg['bcc'] = bcc_emails
        #msg['To'] = ', '.join(to_emails)
        #msg['cc'] = ', '.join(cc_emails)
        #msg['bcc'] = ', '.join(bcc_emails)
    #https://docs.python.org/2/library/email-examples.html
    #https://docs.python.org/3/library/email.examples.html
        msg.preamble = 'You will not see this in a MIME-aware mail reader.\n'

        emails = []
        emails.append(to_emails)
        emails.append(cc_emails)
        emails.append(bcc_emails)

        msg['Date'] = formatdate(localtime=True)

        # Default encoding mode set to Quoted Printable. Acts globally!
        Charset.add_charset('utf-8', Charset.QP, Charset.QP, 'utf-8')

        msg['Subject'] = '{}'.format(Header(subject, 'utf-8'))
        if body:
    # todo: opt for rich format such as html
            msg.attach(MIMEText(body, 'plain', 'utf-8'))

        # file name may contain ',' e.g. Pixel Recursive Super
        # Resolution,1702.00783.pdf
        attachmentList = attachments.strip().split('@')
        if attachmentList:
            try:
                for filename in attachmentList:
                    filename = filename.strip()
                    if not os.path.isfile(filename):
                        continue
                    # Guess the content type based on the file's extension.
                    # Encoding will be ignored, although we should check for
                    # simple things like gzip'd or compressed files
                    ctype, encoding = mimetypes.guess_type(filename)
                    if ctype is None or encoding is not None:
                        # No guess could be made, or the file is encoded
                        # (compressed), so use a generic bag-of-bits type.
                        ctype = 'application/octet-stream'
                    maintype, subtype = ctype.split('/', 1)
                    with open(filename, 'rb') as fp:
    #https://docs.python.org/3/library/email.examples.html
    #                        msg.add_attachment(
    #                            fp.read(),
    #                            maintype=maintype,
    #                            subtype=subtype,
    #                            filename=filename)

    #https://docs.python.org/2/library/email-examples.html
                        msgAttachment = MIMEBase(maintype, subtype)
                        msgAttachment.set_payload(fp.read())
                        # Encode the payload using Base64
                        Encoders.encode_base64(msgAttachment)

                        # todo1: escape special characters in file name: & ? ! etc., already escaped(\ , \& etc.) path results error!
                        #[Errno 2] No such file or directory: u'/mnt/0/Never\\ trust\\ a\\ girl--always\\ get\\ it\\ on\\ tape\\ and\\ bring\\ backup-XFj2oDvK3m0.mkv'
                        # workaround: leave spaces in paths unquoted and use '@' to delimit files

                        #todo2: attached filenames become ATT0*
                        #msgAttachment.add_header('Content-Disposition', 'attachment; filename="{}"'.format(os.path.basename(filename).encode('utf-8')))
                        msgAttachment.add_header('Content-Disposition', 'attachment; filename="{}"'.format(os.path.basename(filename)))
                        msg.attach(msgAttachment)
            except IOError as xpt:
                # todo2: better detailed exception info
                print('(trace):except xpt:', xpt)
                print('(trace):Error opening attachment file "{}"'.format(filename.encode('utf-8')))
                print('(trace):os.path.basename(filename):{}'.format(os.path.basename(filename).encode('utf-8')))
                sys.exit(1)

        print('(trace):type(port):', type(port))

        smtpObj = smtplib.SMTP(smtpServer, port.encode('ascii', 'ignore'))
        smtpObj.set_debuglevel(1)
        #Set the debug output level. A value of 1 or True for level results in debug messages for connection and for all messages sent to and received from the server. A value of 2 for level results in these messages being timestamped.
        #Changed in version 3.5: Added debuglevel 2.
        #https://docs.python.org/3/library/smtplib.html

        smtpObj.ehlo()
        smtpObj.starttls()
        #SMTP.starttls([keyfile[, certfile]])
        #Put the SMTP connection in TLS (Transport Layer Security) mode. All SMTP commands that follow will be encrypted. You should then call ehlo() again.
        #https://docs.python.org/2/library/smtplib.html
        #SMTP.starttls(keyfile=None, certfile=None, context=None)
        #Put the SMTP connection in TLS (Transport Layer Security) mode. All SMTP commands that follow will be encrypted. You should then call ehlo() again.
        #https://docs.python.org/3/library/smtplib.html

        smtpObj.ehlo()
        smtpObj.login(from_emails, passwd)
        smtpObj.sendmail(from_emails, emails, msg.as_string())
        smtpObj.quit()
    else:
        print('(trace):Config file not found! Exiting!')
        sys.exit(1)

if __name__ == '__main__':
    print('(trace):sys.argv:{}'.format(sys.argv))
    
    parser = argparse.ArgumentParser(prog='PROG', description='Send(New) email using Python Smtplib by reading mail headers from files specified on command line.', add_help=True)
    group = parser.add_argument_group('group')
    group.add_argument('-g', required=True, action='store', dest='cnfgrp', help='receive configparser section/group name(ASCII alphanumeric)')
    group.add_argument('infile', help='receive configparser section/group name(ASCII alphanumeric)')
    #group.add_argument('infile', type=argparse.FileType('r'), default=sys.stdin, help='receive configparser section/group name(ASCII alphanumeric)')
    #parser.add_argument('-g', required=True, action='store', dest='group', help='receive configparser section/group name(ASCII alphanumeric)')
    parser.add_argument('--version', action='version', version='%(prog)s 1.0')

    print('(trace):parser.parse_args():{}'.format(parser.parse_args()))
    args = parser.parse_args()

    #cnfGroup = "'{}'".format(args.cnfgrp)
    cnfGroup = args.cnfgrp
    cnfFile = args.infile
    print('(trace):(local var)cnfGroup:{}'.format(cnfGroup))
    print('(trace):(local var)cnfFile:{}'.format(cnfFile))
    
    send_email(cnfFile, cnfGroup)
