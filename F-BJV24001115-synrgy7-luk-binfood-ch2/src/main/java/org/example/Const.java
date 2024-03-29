package org.example;

public class Const {
    public static final String menuMessage =
                """
                    Silahkan pilih menu makanan minuman:\s
                    1. Nasi Goreng              | 10.000
                    2. Mie Goreng               | 10.000
                    3. Ayam Bali                | 12.000
                    4. Telur Bali               | 10.000
                    5. Orak-arik Ayam           | 10.000
                    6. Orak-arik Telur          |  8.000
                    7. Es Teh                   |  3.000
                    8. Es Jeruk                 |  3.000
                    ------------------------------
                    00. Lihat pesanan dan bayar         
                    01. Keluar dan batalkan             """;

    public static final String[] menuList = {
            "Nasi Goreng",
            "Mie Goreng",
            "Ayam Bali",
            "Telur Bali",
            "Orak-arik Ayam",
            "Orak-arik Telur",
            "Es Teh",
            "Es Jeruk"};

    public static final int[] menuPrice = {
            10000,
            10000,
            12000,
            10000,
            10000,
            8000,
            3000,
            3000
    };
}
