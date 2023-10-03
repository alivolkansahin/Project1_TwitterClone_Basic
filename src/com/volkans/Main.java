package com.volkans;

import com.volkans.implementations.TwitterMenuImpl;

/*
 * Kendime Not: Neler Yaptım ?
 * 1- Sealed Person Sınıfından Admin ve User Sınıfları için Miras Aldım, aynı zamanda abstract tanımladım ve direkt nesne üretimini engelledim.
 * 2- Sealed Message Sınıfından Tweet ve Comment Sınıfları için Miras Aldım 
 *    Not : Sealed classları aynı paket içerisindeyken kullanabiliyorum, farklı paketler olduğunda kullanamıyorum.
 * 3- User'ın ve Admin'in ortak aksiyonlarını ISharedActions adlı bir ortak interfacede topladım
 * 4- ISharedActions interface'ini IUserManager ve IAdminManager interfaceleri için miras aldım
 * 5- AdminManagerImpl ve UserManagerImpl implementasyon sınıflarına denk gelen interfaceleri implement ettim.
 * 6- Aynı zamanda Menu tasarımını ve kullanıcıyı yönlendirecek bir ITwitterMenu interface ve onun implementasyonu TwitterMenuImpl oluşturdum.
 * 7- Utils diye bir Sınıf tanımladım, burada menü işlemleri için kullanıcıdan alacağım değerleri bir while döngüsü içerisinde try-catch kontrolü yaptırdım, menünün olası patlamasının önüne geçtim.
 * 	  Bu sınıfta aynı zamanda birkaç renk kodunu static final tanımlı şekilde tuttum. Böylece renklendirme işlemleri için Utils sınıfını kullandım.
 * 8- EHashTag sınıfını programımda kayıtlı hashtaglerimi sakladım, EUserStatus sınıfında programdaki userların statüslerini sakladım.
 * 9- İşlemlerim tamamlanınca sınıfları final olarak tanımladım, ileride oluşturulacak sınıfların miras almasını istemedim.
 * 10- Bir Database Sınıfı oluşturdum ve construct içerisine çağırdığım çeşitli methodlarla TwitterMenuImpl sınıfı içerisinde Database sınıftan nesne ürettim.
 *     Böylelikle elimin altında hali hazırda kayıtlı admin,user ve onların tweetleri,commentleri,aksiyonları olmasını sağladım.
 * 11- Aynı zamanda TwitterMenuImpl sınıfı içerisinde AdminManagerImpl ve UserManagerImpl implementasyon sınıflarından nesneler ürettim.
 * 12- Person sınıfında tanımladığım Random sınıfından türetilmiş bir nesne ile Userlara 7 Haneli, Tweetlere 6 Haneli, Commentlere 5 Haneli random IDler ürettim 
 * 	   (UUID çok uzun ve sample size küçük diye bunu tercih ettim)
 * 13- ToString düzenlemeleri yaptım.
 * 14- Tweet ve Commentler için Hashtagli ve Hashtagsiz 2 tane constructor tanımladım. Hashtag durumuna göre ayrı constructorlar çağrılacak.
 * 	   Burada Enum Hashtaglerimi kullanıcıya gösterdim. Birden fazla hashtag girilmesi için while döngüsü kullandım. 
 * 	   Kullanıcı her seferinde hangi hashtagi girmek istiyorsa onun numarasını girecek. Ne kadar girmek istiyorsa o kadar girebilecek while döngüsü sayesinde.
 * 	   Eğer gösterdiğim hashtagler dışında bir numara girerse diye (IndexOutOfBound) try-catch sistemi ile programın patlamasını engelledim.
 * 15- LocalDateTime.now().withNano(0) ile kayıt anındaki zamanı direk constructor içerisine yolladım.
 * 16- UserManagerImpl implementasyon sınıfımda checkUserCondition methodu ile userın durumuna göre (ban,kısıt,bulunamadı vs gibi) bazı methodlardan işlem yapmadan erken çıkış sağladım.(return)
 * 17- Bütün sınıflarda (interface hariç) class variablelarım ve instance variablelarım private tanımlı ve getter setterlarım tanımlı (bazı set methodları bilerek kaldırıldı).
 * 18- Sadece gösterimin yapılacağı noktalarda stream() kullanılmaya, işlemlerin yapılacağı noktalarda ise foreach kullanmaya özen gösterdim.
 * 	   Buraya bir DipNot: Aslında çoğu işlemde stream.map.foreach kullanıp içerisinde if(a==b) then break; tarzında kullanmıştım. Fakat sonradan gördüm ki (ve araştırdım da)
 * 						 stream()'lerin foreach'inde break,continue ve return keywordleri konulabilmesine rağmen çalışmıyorlar! O yüzden revize edip stream()'lerden vazgeçip normal foreachlere döndüm.
 * 19- Gerekli yerlerde labeledBreak ve labeledContinue kullandım (etiketli kırma/devamettirme)
 * 20- MessageBox için (DM Kutusu) Map<User,List<Message>> kullanmayı tercih ettim, böylelikle her user'ı unique kullanıp, mesajlarını tek bir listeye atmayı hedefledim.
 * 
 * Kendime Eksi Not: 
 * generic kullanabileceğim yerler vardı (postTweet ve postComment)
 * Birkaç satırı method içine alabileceğim durum söz konusuydu - fakat şimdilik bu kadar yeter diye + biraz rahatsızlandığım için burada bıraktım... İleride düzeltilebilir...
 * 
 */
public class Main {

	static TwitterMenuImpl twitter = new TwitterMenuImpl();
	
	public static void main(String[] args) {
		
		twitter.runMainPage();
		
	}

}
