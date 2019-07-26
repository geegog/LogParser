--query to find IPs that mode more than a certain number of requests for a given time period.
select l.ip, count(l.ip) as totalRequest from log l
where date_time between '2017-01-01 13:00:00' and '2017-01-02 14:00:00'
group by l.ip
having count(l.ip) > 100;

--query to find requests made by a given IP.
select method_and_protocol from log l
where l.ip = '192.168.106.134';
