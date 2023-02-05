package com.smality.expandablelistwithcompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smality.expandablelistwithcompose.ui.theme.ExpandableListWithComposeTheme

//Listelemede bulunacak yazıları List sınıfına ekleme
private val messages: List<MyMessage> = listOf(
    MyMessage(
        "Compose ile Harita Kullanımı",
        "Harita ve konum teknolojileri ile hayatımızdaki birçok probleme çözüm sağlayarak işlerimizi kolaylaştırıyoruz. Bu yüzden yazılım projelerimizde harita kullanımı çok önemlidir.\n \nBu makale, Compose yapısında harita(maps) oluşturmayı ve konum bilgisini kullanmayı örnekleyecektir." ),
    MyMessage(
        "Compose ile BottomSheet Kullanımı",
        "Popüler ve kullanıcı deneyimi yüksek olan arayüz elementlerini tasarımda kullanmak müşterilerin dikkatini çekmek için çok önemlidir.\n" +
                "\n" +
                "Bu makale, Jetpack Compose ile ekranın alt kısmında kapatılabilir ve açılabilir alan sağlayan Bottom Sheet bileşenini örnekleyecektir."
    ),
    MyMessage(
        "Kotlin ile Carousel Android App",
        "Kullanıcı deneyimini arttıran elementler ile etkileşimli kullanıcı arayüz tasarlamak projenin yaşam döngüsü için çok önemlidir."+
                "\n\n" +
                "Bu makale Kotlin dili ile  Android Viewpager2 widget’ına Carousel efektini ekleyerek slider yapımını örnekleyecektir."    ),
    MyMessage(
        "Compose Tasarım Araç ve Özellikleri",
        "Yazılım geliştirme süreçlerini kolaylaştırmak ve hızlandırmak için birçok araç tavsiye edilmektedir.\n" +
                "\n" +
                "Bu makale Android Stduio’da Compose ile tasarım geliştirme aşamasında kolaylık ve hızlılık sağlayan araç ve özellikleri anlatacağım."    ),
    MyMessage(
        "Play Integrity API ile Güvenlik Önlemleri",
        "Yazılımlarımızı saldırılara karşı güncel yapılarla güvenliğini sağlamak her daim önemlidir.\n" +
                "\n" +
                "Bu makale Google Play Integrity API ile uygulamalarınızı ve oyunlarınızı zararlı saldırılardan korumayı, kötüye kullanımı azaltmayı nasıl sağlayacağımızı açıklayacağım."
    ),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpandableListWithComposeTheme {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    MyMessages(messages)
                }
            }
        }
    }
}
data class MyMessage(val title: String, val body: String)
@Composable
fun MyComponent(message: MyMessage) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(8.dp)
    )
    {
        MyTexts(message)
    }
}
//Dikey olarak kayan bir liste üretmek için LazyColumn kullanalım
@Composable
fun MyMessages(messages: List<MyMessage>) {
    LazyColumn {
        items(messages) { message ->
            //Satır yapısını kullanan fonksiyon
            MyComponent(message = message)
        }
    }
}

@Composable
fun MyTexts(message: MyMessage) {
    //Expandable yapıya sahip olması için remember API'sını kullanıyoruz
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier
            .clickable {
                //Her tıklandığında güncel expanded değerinin olumsuz halini expanded değişkenine atadık
                expanded = !expanded
            }) {
            //Başlık alanı için Text elementine görsel özellikler tanımlama
            MyText(
                text = message.title,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.subtitle1,
                fontFamily = FontFamily.SansSerif
            )
            //İçerik alanı için Text elementine görsel özellikler tanımlama
            MyText(
                text = message.body,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Monospace,
                //expanded değişkenin true ise içeriğin uzunluğu kadar görünür satır alanı oluşturur
                lines = if (expanded) Int.MAX_VALUE else 1
            )
            Spacer(modifier = Modifier.padding(6.dp))

        }
    }
}

//Listenen yazıları görüntüleyebilmek için Text elementini tanımlama
@Composable
fun MyText(
    text: String,
    color: Color,
    fontWeight: FontWeight,
    style: TextStyle,
    fontFamily: FontFamily,
    lines: Int = Int.MAX_VALUE
) {
    Text(
        text,
        color = color,
        fontWeight = fontWeight,
        style = style,
        fontFamily = fontFamily,
        maxLines = lines
    )
}

@Preview(showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    ExpandableListWithComposeTheme {
        Column(modifier = Modifier.padding(top = 16.dp)) {
            MyMessages(messages = messages)
        }
    }
}