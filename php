# php -f /var/www/html/infophp.php

$ php -a
php > #cli.pager=less

#array https://www.youtube.com/watch?v=U5SSASv_7ng
#Getting data out of a collection (loops). Understanding forms (GET, POST reqs, etc). Multidimensional arrays. String interpolation. This is so often the vast majority of what we're doing. Maybe sprinkle a little AJAX in there and you're in pretty good shape.
Example #6 Accessing array elements
<?php
$array = array(
"foo" => "bar",
42    => 24,
"multi" => array(
	"dimensional" => array(
		"array" => "foo")
));
var_dump($array["foo"]);
var_dump($array[42]);
var_dump($array["multi"]["dimensional"]["array"]);
?>
