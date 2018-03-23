# Metin-Analizi
Duygu analizi yapan bu uygulamada twitter üzerinden kullanıcının verileri çekilmektedir. 
Web servisi bağlantılarını isteyen kişiler lütfen iletişime geçsin.



geliştirici adresleri: seydaasevgen@gmail.com
                       egemen.ozogul@gmail.com
                       
                     
                     
Kullanılması gereken web-services.php


application/x-httpd-php webservices.php ( PHP script, ASCII text )

```php


<?php
$token = 'kendi bilgilerinizle değiştirin';
$token_secret = 'kendi bilgilerinizle değiştirin';
$consumer_key = 'kendi bilgilerinizle değiştirin';
$consumer_secret = 'kendi bilgilerinizle değiştirin';

$host = 'api.twitter.com';
$method = 'GET';
$path = '/1.1/statuses/user_timeline.json'; // api call path




$query = array( // query parameters
    'screen_name' => $_GET["isim"],
    'count' => $_GET["sayi"],
    'page' =>$_GET["sayfa"]
);

$oauth = array(
    'oauth_consumer_key' => $consumer_key,
    'oauth_token' => $token,
    'oauth_nonce' => (string)mt_rand(), // a stronger nonce is recommended
    'oauth_timestamp' => time(),
    'oauth_signature_method' => 'HMAC-SHA1',
    'oauth_version' => '1.0'
);
$oauth = array_map("rawurlencode", $oauth); // must be encoded before sorting
$query = array_map("rawurlencode", $query);
$arr = array_merge($oauth, $query); // combine the values THEN sort
asort($arr); // secondary sort (value)
ksort($arr); // primary sort (key)
$querystring = urldecode(http_build_query($arr, '', '&'));
$url = "https://$host$path";
$base_string = $method."&".rawurlencode($url)."&".rawurlencode($querystring);

$key = rawurlencode($consumer_secret)."&".rawurlencode($token_secret);

$signature = rawurlencode(base64_encode(hash_hmac('sha1', $base_string, $key, true)));

$url .= "?".http_build_query($query);
$url=str_replace("&amp;","&",$url); 

$oauth['oauth_signature'] = $signature; 
ksort($oauth);

function add_quotes($str) { return '"'.$str.'"'; }
$oauth = array_map("add_quotes", $oauth);

$auth = "OAuth " . urldecode(http_build_query($oauth, '', ', '));

$options = array( CURLOPT_HTTPHEADER => array("Authorization: $auth"),
                  CURLOPT_HEADER => false,
                  CURLOPT_URL => $url,
                  CURLOPT_RETURNTRANSFER => true,
                  CURLOPT_SSL_VERIFYPEER => false);

$feed = curl_init();
curl_setopt_array($feed, $options);
$json = curl_exec($feed);
curl_close($feed);

$twitter_data = json_decode($json);


foreach ($twitter_data as &$value) {
   $tweetout .= preg_replace("/(http:\/\/|(www\.))(([^\s<]{4,68})[^\s<]*)/", '<a href="http://$2$3" target="_blank">$1$2$4</a>', $value->text);
   $tweetout = preg_replace("/@(\w+)/", "<a href=\"http://www.twitter.com/\\1\" target=\"_blank\">@\\1</a>", $tweetout);
   $tweetout = preg_replace("/#(\w+)/", "<a href=\"http://search.twitter.com/search?q=\\1\" target=\"_blank\">#\\1</a>", $tweetout);
$tweetout .="%%%%%%%";
}

echo $tweetout;

?>

```
