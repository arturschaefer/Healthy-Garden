package com.schaefer.healthygarden.domain.model

import android.media.Image

object Mock {
    val arrayListOfGarden = arrayListOf<Garden>(
        Garden(
            "Jardim 01",
            "https://vejasp.abril.com.br/wp-content/uploads/2016/11/garden.jpeg?quality=70&strip=info&resize=680,453",
            "Jardim 01",
            "12/12/2020",
            "12/12/2020",
            true,
            arrayListOf(),
            "abced12"
        ),
        Garden(
            "Jardim 02",
            "https://vejasp.abril.com.br/wp-content/uploads/2016/11/garden.jpeg?quality=70&strip=info&resize=680,453",
            "Jardim 02",
            "12/12/2020",
            "12/12/2020",
            false,
            arrayListOf(),
            "12test"
        )
    )
    val arrayOfGallery = arrayListOf(
        ImageGallery(
            "Foto 01",
            "https://vejasp.abril.com.br/wp-content/uploads/2016/11/garden.jpeg?quality=70&strip=info&resize=680,453",
            "12/12/2020"
        ),
        ImageGallery(
            "Foto 02",
            "https://vejasp.abril.com.br/wp-content/uploads/2016/11/garden.jpeg?quality=70&strip=info&resize=680,453",
            "12/12/2020"
        )
    )
}