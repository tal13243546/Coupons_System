# Coupons_System
 Software for managing companies, customers and coupons. 

 Admin can add / update / delete / view companies and customers. 
 
 Any company can add / update / delete / display coupons belonging to it. 
 
 Every customer can see / buy coupons from registered companies and view the coupons they bought.
 
# Extra code added to the software beyond what is required:

## Classes 

 CustomerVsCoupon, Table100 (Table printer class), ArtUtil.
 
## Methods

 -deleteCouponPurchaseByCoupon(int id);
 
 -deleteCouponPurchaseByCustomer(int customerID);
 
 -getCvcByCustomer(int customerID);
 
 -getOneCouponByTitleAndComID(String title, int companyID);
 
 -getAllCvc();
 

# DataBase Information:
 
user: root

pass: 132435

## Companies

### id | name |     email    | password | coupons

1 |  Tal |  t@gmail.com |   1324   |    []  -> password updated (4231) -> Company Deleted!
______________________
2 |  JB  | jb@gmail.com |   jb123  |    []  
______________________

     
 ## Customers

 ### id | firstName | lastName |     email    | password | coupons
 
   1 |    Tom    |    Cat   | tc@gmail.com |   tc123  |    []  -> password updated (tomcat13) -> Customer Deleted!
   ______________________
   2 |    Tal    |   Levin  | tl@gmail.com |   tl123  |    []  
   ______________________
 ## Coupons

### id | companyId |   category  |       title      |             description            |  startDate |   endDate  | amount |  price |   image  
 
  1 |     2     |     FOOD    |      Hamburg     |         2 Hamburgers 150gr         | 2020-08-14 | 2020-10-14 |   10   |  89.9  | image.png 
  ______________________
  2 |     2     |  RESTAURANT |        BBB       | Hamburger Katzavim + Fries + Drink | 2020-08-14 | 2020-10-14 |   10   |  99.9  | image.png 
  ______________________
  3 |     2     |   VACATION  |       Eilat      |      Half Pension Malkat Shva      | 2020-08-14 | 2020-10-14 |   10   |  499.9 | image.png
  ______________________
  4 |     2     | ELECTRICITY | Mahsanei Hashmal |            Iphone XS Max           | 2020-08-14 | 2020-10-14 |   10   | 1099.9 | image.png
  ______________________
 Hamburg coupon -> Amount updated(20) -> Deleted by Delete method.
  
 BBB coupon -> endDate updated(2020-09-14) -> Deleted by Expired Coupon Thread;
  
## Customers_Vs_Coupons
 
### |customerID | couponID| 
 
    |    2     |    3     |
 
