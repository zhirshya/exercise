#fedora
#OpenCart
https://www.rosehosting.com/blog/install-opencart-2-on-an-ubuntu-14-04-vps-with-nginx-and-php-fpm/

sudo dnf install httpd -y

systemctl enable httpd
#[output given as following line when executed the above command as a standard user (non-admin) under Fedora 22Beta]
#Created symlink from /etc/systemd/system/multi-user.target.wants/httpd.service to /usr/lib/systemd/system/httpd.service.

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

Enable MariaDB at startup:
	systemctl enable mariadb
Start the daemon:
	systemctl start mariadb

Set MariaDB root password:
	mysql_secure_installation

#PHP
sudo dnf install php phpmyadmin -y

#PHP module #php5-mysqlnd <==> php5-mysqld
sudo apt-get install php5-fpm php5-cli php5-mcrypt php5-gd php5-mysqlnd php5-curl
sudo php5enmod mcrypt

