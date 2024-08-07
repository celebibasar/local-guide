package com.basarcelebi.localguide.model

import com.basarcelebi.localguide.data.Place

object PlaceObject {
        private val _places = mutableListOf(Place(
        name = "Kahve Dünyası",
        description = "Kahve Dünyası, 2004 yılında İstanbul'da kurulmuş bir Türk kahve markasıdır. Kahve Dünyası, Türk kahvesi, espresso, filtre kahve, soğuk kahve, çay, sıcak çikolata, dondurma, tatlı ve tuzlu yiyecekler sunmaktadır.",
        isFavorited = false,
        category = "Cafe",
        address = "Kadıköy, İstanbul",
        phone = "+90 216 418 00 00",
        website = "https://www.kahvedunyasi.com/",
        imageUrl = "https://media-cdn.tripadvisor.com/media/photo-s/06/e3/f4/3c/kahve-dunyasi-akasya.jpg",
        rating = 4.5f
        ),
        Place(
        name = "Topkapı Sarayı",
        description = "Topkapı Sarayı, İstanbul'un Sarayburnu'nda, Osmanlı İmparatorluğu'nun 400 yıl boyunca yönetim merkezi olan saraydır. Saray, 1465 yılında II. Mehmed tarafından inşa edilmiştir.",
        isFavorited = false,
        category = "Museum",
        address = "Fatih, İstanbul",
        phone = "+90 212 512 04 80",
        website = "https://topkapisarayi.gov.tr/",
        imageUrl = "https://cdn.britannica.com/86/148586-004-9ADEC63B/Topkapi-Palace-Istanbul-Turkey.jpg",
        rating = 4.7f
        ),
        Place(
        name = "Galata Kulesi",
        description = "Galata Kulesi, İstanbul'un Galata semtinde yer alan bir kuledir. Kule, 1348 yılında Cenevizliler tarafından inşa edilmiştir. Kule, 66.90 metre yüksekliğinde olup 9 katlıdır.",
        isFavorited = false,
        category = "Tourist Place",
        address = "Beyoğlu, İstanbul",
        phone = "+90 212 293 81 80",
        website = "https://www.galatakulesi.com/",
        imageUrl = "https://loibosphorus.com/wp-content/uploads/2023/09/galata-kulesi.jpg",
        rating = 4.6f
        )
    )
        fun getPlaces(): MutableList<Place> = _places
}