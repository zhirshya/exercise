sudo dnf install httpd -y

systemctl enable httpd
systemctl start httpd

If you are encountered with the following error:
	Job for httpd.service failed. See 'systemctl status httpd.service' and 'journalctl -xn' for details.

Delete all contents in your /etc/hostname file and add the word “localhost”. Also set “localhost“ to the “Servername” value in /etc/httpd/conf/httpd.conf file and try again to start httpd service.

And adjust the firewall to allow the httpd service to connect with remote clients.

firewall-cmd --permanent --add-service=http
firewall-cmd --permanent --add-service=https

Restart firewalld service:

firewall-cmd --reload

sudo dnf install mariadb mariadb-server -y
Enable mysqld service at boot time with following command:
	systemctl enable mysqld
And start mysqld service using command:
	systemctl start mysqld

Set MariaDB root password:
	mysql_secure_installation


sudo dnf install php -y

