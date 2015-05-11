#OpenCart
https://www.rosehosting.com/blog/install-opencart-2-on-an-ubuntu-14-04-vps-with-nginx-and-php-fpm/

Install LAMP in Ubuntu Server 14.04 LTS

#httpd fedora
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

#apache2 ubuntu
sudo apt-get install apache2 apache2-utils

sudo vim /etc/apache2/mods-enabled/dir.conf #reorder def index file

sudo service apache2 restart

#mariadb
sudo dnf install mariadb mariadb-server -y

Enable MariaDB at startup:
	systemctl enable mariadb
Start the daemon:
	systemctl start mariadb

Set MariaDB root password:
	mysql_secure_installation

#mariadb ubuntu
#sudo systemctl stop mysql  #fedora only?
sudo service mysql stop
sudo apt-get remove --purge mysql-server mysql-client mysql-common
sudo apt-get autoremove
sudo apt-get autoclean
sudo rm -rf /var/lib/mysql/
sudo rm -rf /etc/mysql/

sudo apt-get install mariadb-server

mysql_secure_installation

#PHP
sudo dnf install php phpmyadmin -y

#PHP module #php5-mysqlnd <==> php5-mysqld
sudo apt-get install php5-fpm php5-cli php5-mcrypt php5-gd php5-mysqlnd php5-curl
sudo php5enmod mcrypt

#PHP ubuntu
sudo apt-get install libapache2-mod-php5 php5 php5-mysql php-pear php5-gd php5-mcrypt php5-curl

/*
dbconfig-common: writing config to /etc/dbconfig-common/phpmyadmin.conf

Creating config file /etc/dbconfig-common/phpmyadmin.conf with new version

Creating config file /etc/phpmyadmin/config-db.php with new version
granting access to database phpmyadmin for phpmyadmin@localhost: success.
verifying access for phpmyadmin@localhost: success.
creating database phpmyadmin: success.
verifying database phpmyadmin exists: success.
populating database via sql...  done.
dbconfig-common: flushing administrative password
apache2_invoke: Enable configuration phpmyadmin
 * Reloading web server apache2                                                                                                                   * 
 * Reloading web server apache2                                                                                                                   * 
*/

sudo apt-get install phpmyadmin 

