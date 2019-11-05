// currencies

var con=new Array();
var cur=new Array();

//=========================
con[0]='United Arab Emirates Dirham-AED';cur[0]='AED';
con[1]='Afghan Afghani-AFN';cur[1]='AFN';
con[2]='Albanian Lek-ALL';cur[2]='ALL';
con[3]='Armenian Dram-AMD';cur[3]='AMD';
con[4]='Netherlands Antillean Guilder-ANG';cur[4]='ANG';
con[5]='Angolan Kwanza-AOA';cur[5]='AOA';
con[6]='Argentine Peso-ARS';cur[6]='ARS';
con[7]='Australian Dollar-AUD';cur[7]='AUD';
con[8]='Aruban Florin-AWG';cur[8]='AWG';
con[9]='Azerbaijani Manat-ANZ';cur[9]='AZN';
con[10]='Bosnia-Herzegovina Convertible Mark-BAM';cur[10]='BAM';
con[11]='Barbadian Dollar-BBD';cur[11]='BBD';
con[12]='Bangladeshi Taka-BDT';cur[12]='BDT';
con[13]='Bulgarian Lev-BGN';cur[13]='BGN';
con[14]='Bahraini Dinar-BHD';cur[14]='BHD';
con[15]='Burundian Franc-BIF';cur[15]='BIF';
con[16]='Bermudan Dollar-BMD';cur[16]='BMD';
con[17]='Brunei Dollar-BND';cur[17]='BND';
con[18]='Bolivian Boliviano-BOB';cur[18]='BOB';
con[19]='Brazilian Real-BRL';cur[19]='BRL';
con[20]='Bahamian Dollar-BSD';cur[20]='BSD';
con[21]='Bhutanese Ngultrum-BTN';cur[21]='BTN';
con[22]='Botswanan Pula-BWP';cur[22]='BWP';
con[23]='Belarusian Ruble-BYR';cur[23]='BYR';
con[24]='New Belarusian Ruble-BYN';cur[24]='BYN';
con[25]='Belize Dollar-BZD';cur[25]='BZD';
con[26]='Canadian Dollar-CAD';cur[26]='CAD';
con[27]='Congolese Franc-CDF';cur[27]='CDF';
con[28]='Swiss Franc-CHF';cur[28]='CHF';
con[29]='Chilean Unit of Account (UF)-CLF';cur[29]='CLF';
con[30]='Chilean Peso-CLP';cur[30]='CLP';
con[31]='Chinese Yuan-CNY';cur[31]='CNY';
con[32]='Colombian Peso-COP';cur[32]='COP';
con[33]='Costa Rican Colón-CRC';cur[33]='CRC';
con[34]='Cuban Convertible Peso-CUC';cur[34]='CUC';
con[35]='Cuban Peso-CUP';cur[35]='CUP';
con[36]='Cape Verdean Escudo-CVE';cur[36]='CVE';
con[37]='Czech Republic Koruna-CZK';cur[37]='CZK';
con[38]='Djiboutian Franc-DJF';cur[38]='DJF';
con[39]='Danish Krone-DKK';cur[39]='DKK';
con[40]='Dominican Peso-DOP';cur[40]='DOP';
con[41]='Algerian Dinar-DZD';cur[41]='DZD';
con[42]='Egyptian Pound-EGP';cur[42]='EGP';
con[43]='Eritrean Nakfa-ERN';cur[43]='ERN';
con[44]='Ethiopian Birr-ETB';cur[44]='ETB';
con[45]='Euro-EUR';cur[45]='EUR';
con[46]='Fijian Dollar-FJD';cur[46]='FJD';
con[47]='Falkland Islands Pound-FKP';cur[47]='FKP';
con[48]='British Pound Sterling-GBP';cur[48]='GBP';
con[49]='Georgian Lari-GEL';cur[49]='GEL';
con[50]='Guernsey Pound-GGP';cur[50]='GGP';
con[51]='Ghanaian Cedi-GHS';cur[51]='GHS';
con[52]='Gibraltar Pound-GIP';cur[52]='GIP';
con[53]='Gambian Dalasi-GMD';cur[53]='GMD';
con[54]='Guinean Franc-GNF';cur[54]='GNF';
con[55]='Guatemalan Quetzal-GTQ';cur[55]='GTQ';
con[56]='Guyanaese Dollar-GYD';cur[56]='GYD';
con[57]='Hong Kong Dollar-HKD';cur[57]='HKD';
con[58]='Honduran Lempira-HNL';cur[58]='HNL';
con[59]='Croatian Kuna-HRK';cur[59]='HRK';
con[60]='Haitian Gourde-HTG';cur[60]='HTG';
con[61]='Hungarian Forint-HUF';cur[61]='HUF';
con[62]='Indonesian Rupiah-IDR';cur[62]='IDR';
con[63]='Israeli New Sheqel-ILS';cur[63]='ILS';
con[64]='Manx pound-IMP';cur[64]='IMP';
con[65]='Indian Rupee-INR';cur[65]='INR';
con[66]='Iraqi Dinar-IQD';cur[66]='IQD';
con[67]='Iranian Rial-IRR';cur[67]='IRR';
con[68]='Icelandic Króna-ISK';cur[68]='ISK';
con[69]='Jersey Pound-JEP';cur[69]='JEP';
con[70]='Jamaican Dollar-JMD';cur[70]='JMD';
con[71]='Jordanian Dinar-JOD';cur[71]='JOD';
con[72]='Japanese Yen-JPY';cur[72]='JPY';
con[73]='Kenyan Shilling-KES';cur[73]='KES';
con[74]='Kyrgystani Som-KGS';cur[74]='KGS';
con[75]='Cambodian Riel-KHR';cur[75]='KHR';
con[76]='North Korean Won-KPW';cur[76]='KPW';
con[77]='South Korean Won-KRW';cur[77]='KRW';
con[78]='Kuwaiti Dinar-KWD';cur[78]='KWD';
con[79]='Cayman Islands Dollar-KYD';cur[79]='KYD';
con[80]='Kazakhstani Tenge-KZT';cur[80]='KZT';
con[81]='Laotian Kip-LAK';cur[81]='LAK';
con[82]='Lebanese Pound-LBP';cur[82]='LBP';
con[83]='Sri Lankan Rupee-LKR';cur[83]='LKR';
con[84]='Liberian Dollar-LRD';cur[84]='LRD';
con[85]='Lesotho Loti-LSL';cur[85]='LSL';
con[86]='Lithuanian Litas-LTL';cur[86]='LTL';
con[87]='Latvian Lats-LVL';cur[87]='LVL';
con[88]='Libyan Dinar-LYD';cur[88]='LYD';
con[89]='Moroccan Dirham-MAD';cur[89]='MAD';
con[90]='Moldovan Leu-MDL';cur[90]='MDL';
con[91]='Malagasy Ariary-MGA';cur[91]='MGA';
con[92]='Macedonian Denar-MKD';cur[92]='MKD';
con[93]='Myanma Kyat-MMK';cur[93]='MMK';
con[94]='Mongolian Tugrik-MNT';cur[94]='MNT';
con[95]='Macanese Pataca-MOP';cur[95]='MOP';
con[96]='Mauritanian Ouguiya-MRO';cur[96]='MRO';
con[97]='Mauritian Rupee-MUR';cur[97]='MUR';
con[98]='Maldivian Rufiyaa-MVR';cur[98]='MVR';
con[99]='Mexican Peso-MXN';cur[99]='MXN';
con[100]='Malaysian Ringgit-MYR';cur[100]='MYR';
con[101]='Mozambican Metical-MZN';cur[101]='MZN';
con[102]='Namibian Dollar-NAD';cur[102]='NAD';
con[103]='Nigerian Naira-NGN';cur[103]='NGN';
con[104]='Nicaraguan Córdoba-NIO';cur[104]='NIO';
con[105]='Norwegian Krone-NOK';cur[105]='NOK';
con[106]='Nepalese Rupee-NPR';cur[106]='NPR';
con[107]='New Zealand Dollar-NZD';cur[107]='NZD';
con[108]='Omani Rial-OMR';cur[108]='OMR';
con[109]='Panamanian Balboa-PAB';cur[109]='PAB';
con[110]='Peruvian Nuevo Sol-PEN';cur[110]='PEN';
con[111]='Papua New Guinean Kina-PGK';cur[111]='PGK';
con[112]='Philippine Peso-PHP';cur[112]='PHP';
con[113]='Pakistani Rupee-PKR';cur[113]='PKR';
con[114]='Polish Zloty-PLN';cur[114]='PLN';
con[115]='Paraguayan Guarani-PYG';cur[115]='PYG';
con[116]='Qatari Rial-QAR';cur[116]='QAR';
con[117]='Romanian Leu-RON';cur[117]='RON';
con[118]='Serbian Dinar-RSD';cur[118]='RSD';
con[119]='Russian Ruble-RUB';cur[119]='RUB';
con[120]='Rwandan Franc-RWF';cur[120]='RWF';
con[121]='Papua New Guinean Kina-PGK';cur[121]='PGK';
con[122]='Philippine Peso-PHP';cur[122]='PHP';
con[123]='Pakistani Rupee-PKR';cur[123]='PKR';
con[124]='Polish Zloty-PLN';cur[124]='PLN';
con[125]='Paraguayan Guarani-PYG';cur[125]='PYG';
con[126]='Qatari Rial-QAR';cur[126]='QAR';
con[127]='Romanian Leu-RON';cur[127]='RON';
con[128]='Serbian Dinar-RSD';cur[128]='RSD';
con[129]='Russian Ruble-RUB';cur[129]='RUB';
con[130]='Rwandan Franc-RWF';cur[130]='RWF';
con[131]='Saudi Riyal-SAR';cur[131]='SAR';
con[132]='Solomon Islands Dollar-SBD';cur[132]='SBD';
con[133]='Seychellois Rupee-SCR';cur[133]='SCR';
con[134]='Sudanese Pound-SDG';cur[134]='SDG';
con[135]='Swedish Krona-SEK';cur[135]='SEK';
con[136]='Singapore Dollar-SGD';cur[136]='SGD';
con[137]='Saint Helena Pound-SHP';cur[137]='SHP';
con[138]='Sierra Leonean Leone-SLL';cur[138]='SLL';
con[139]='Somali Shilling-SOS';cur[139]='SOS';
con[140]='Surinamese Dollar-SRD';cur[140]='SRD';
con[141]='São Tomé and Príncipe Dobra-STD';cur[141]='STD';
con[142]='Salvadoran Colón-SVC';cur[142]='SVC';
con[143]='Syrian Pound-SYP';cur[143]='SYP';
con[144]='Swazi Lilangeni-SZL';cur[144]='SZL';
con[145]='Thai Baht-THB';cur[145]='THB';
con[146]='Tajikistani Somoni-TJS';cur[146]='TJS';
con[147]='Turkmenistani Manat-TMT';cur[147]='TMT';
con[148]='Tunisian Dinar-TND';cur[148]='TND';
con[149]='Tongan Paʻanga-TOP';cur[149]='TOP';
con[150]='Turkish Lira-TRY';cur[150]='TRY';
con[151]='Trinidad and Tobago Dollar-TTD';cur[151]='TTD';
con[152]='New Taiwan Dollar-TWD';cur[152]='TWD';
con[153]='Tanzanian Shilling-TZS';cur[153]='TZS';
con[154]='Ukrainian Hryvnia-UAH';cur[154]='UAH';
con[155]='Ugandan Shilling-UGX';cur[155]='UGX';
con[156]='United States Dollar-USD';cur[156]='USD';
con[157]='Uruguayan Peso-UYU';cur[157]='UYU';
con[158]='Uzbekistan Som-UZS';cur[158]='UZS';
con[159]='Venezuelan Bolívar Fuerte-VEF';cur[159]='VEF';
con[160]='Vietnamese Dong-VND';cur[160]='VND';
con[161]='Vanuatu Vatu-VUV';cur[161]='VUV';
con[162]='Samoan Tala-WST';cur[162]='WST';
con[163]='CFA Franc BEAC-XAF';cur[163]='XAF';
con[164]='Silver (troy ounce)-XAG';cur[164]='XAG';
con[165]='Gold (troy ounce)-XAU';cur[165]='XAU';
con[166]='East Caribbean Dollar-XCD';cur[166]='XCD';
con[167]='Special Drawing Rights-XDR';cur[167]='XDR';
con[168]='CFA Franc BCEAO-XOF';cur[168]='XOF';
con[169]='CFP Franc-XPF';cur[169]='XPF';
con[170]='Yemeni Rial-YER';cur[170]='YER';
con[171]='South African Rand-ZAR';cur[171]='ZAR';
con[172]='Zambian Kwacha (pre-2013)-ZMK';cur[172]='ZMK';
con[173]='Zambian Kwacha-ZMW';cur[173]='ZMW';
con[174]='Zimbabwean Dollar-ZWL';cur[174]='ZWL';



//Kyrgystani Som-KGS	KGS
//Cambodian Riel-KHR	KHR
//Comorian Franc-KMF	KMF
//North Korean Won-KPW	KPW
//South Korean Won-KRW	KRW
//Kuwaiti Dinar-KWD	KWD
//Cayman Islands Dollar-KYD	KYD
//Kazakhstani Tenge-KZT	KZT
//Laotian Kip-LAK	LAK
//Lebanese Pound-LBP	LBP
//Sri Lankan Rupee-LKR	LKR
//Liberian Dollar-LRD	LRD
//Lesotho Loti-LSL	LSL
//Lithuanian Litas-LTL	LTL
//Latvian Lats-LVL	LVL
//Libyan Dinar-LYD	LYD
//Moroccan Dirham-MAD	MAD
//Moldovan Leu-MDL	MDL
//Malagasy Ariary-MGA	MGA
//Macedonian Denar-MKD	MKD
//Myanma Kyat-MMK	MMK
//Mongolian Tugrik-MNT	MNT
//Macanese Pataca-MOP	MOP
//Mauritanian Ouguiya-MRO	MRO
//Mauritian Rupee-MUR	MUR
//Maldivian Rufiyaa-MVR	MVR
//Malawian Kwacha-MWK	MWK
//Mexican Peso-MXN	MXN
//Malaysian Ringgit-MYR	MYR
//Mozambican Metical-MZN	MZN
//Namibian Dollar-NAD	NAD
//Nigerian Naira-NGN	NGN
//Nicaraguan Córdoba-NIO	NIO
//Norwegian Krone-NOK	NOK
//Nepalese Rupee-NPR	NPR
//New Zealand Dollar-NZD	NZD
//Omani Rial-OMR	OMR
//Panamanian Balboa-PAB	PAB
//Peruvian Nuevo Sol-PEN	PEN
//Papua New Guinean Kina-PGK	PGK
//Philippine Peso-PHP	PHP
//Pakistani Rupee-PKR	PKR
//Polish Zloty-PLN	PLN
//Paraguayan Guarani-PYG	PYG
//Qatari Rial-QAR	QAR
//Romanian Leu-RON	RON
//Serbian Dinar-RSD	RSD
//Russian Ruble-RUB	RUB
//Rwandan Franc-RWF	RWF
//Saudi Riyal-SAR	SAR
//Solomon Islands Dollar-SBD	SBD
//Seychellois Rupee-SCR	SCR
//Sudanese Pound-SDK	SDG
//Swedish Krona-SEK	SEK
//Singapore Dollar-SGD	SGD
//Saint Helena Pound-SHP	SHP
//Sierra Leonean Leone-SLL	SLL
//Somali Shilling-SOS	SOS
//Surinamese Dollar-SRD	SRD
//São Tomé and Príncipe Dobra-STD	STD
//Salvadoran Colón-SVC	SVC
//Syrian Pound-SYP	SYP
//Swazi Lilangeni-SZL	SZL
//Thai Baht-THP	THB
//Tajikistani Somoni-TJS	TJS
//Turkmenistani Manat-TMT	TMT
//Tunisian Dinar-TND	TND
//Tongan Paʻanga-TOP	TOP
//Turkish Lira-TRY	TRY
//Trinidad and Tobago Dollar-TTD	TTD
//New Taiwan Dollar-TWD	TWD
//Tanzanian Shilling-TZS	TZS
//Ukrainian Hryvnia-UAH	UAH
//Ugandan Shilling-UGX	UGX
//United States Dollar-USD	USD
//Uruguayan Peso-UYU	UYU
//Uzbekistan Som-UZS	UZS
//Venezuelan Bolívar Fuerte-VEF	VEF
//Vietnamese Dong-VND	VND
//Vanuatu Vatu-VUV	VUV
//Samoan Tala-WST	WST
//CFA Franc BEAC-XAF	XAF
//Silver (troy ounce)-XAG	XAG
//Gold (troy ounce)-XAU	XAU
//East Caribbean Dollar-XCD	XCD
//Special Drawing Rights-XDR	XDR
//CFA Franc BCEAO-XOF	XOF
//CFP Franc-XPF	XPF
//Yemeni Rial-YPR	YER
//South African Rand-ZAR	ZAR
//Zambian Kwacha (pre-2013)-ZMK	ZMK
//Zambian Kwacha-ZMW	ZMW
//Zimbabwean Dollar-ZWL	ZWL

//========================

function getAllCurrencies(){
	return cur;
}

function populateCurrency(currencyId){
	var currencyId = document.getElementById(currencyId);
	
	for (var i=0; i<cur.length; i++) {
		currencyId.options[currencyId.length] = new Option(con[i],cur[i]);
	}
	
}
	
function populateCurrencyEdit(currencyId, selectedValue){

	var currencyId = document.getElementById(currencyId);

	//currencyId.selectedIndex = cur.indexOf(selectedValue);
	//currencyId.options[0] = new Option(selectedValue,selectedValue);
	
	for (var i=0; i<cur.length; i++) {
		currencyId.options[currencyId.length] = new Option(con[i],cur[i]);
	}	
	currencyId.selectedIndex = cur.indexOf(selectedValue)+1;
	
}


//-------------------------------FCNR------------------------------------------
var conFCNR=new Array();
var curFCNR=new Array();
conFCNR[0]='United States Dollar-USD';curFCNR[0]='USD';
conFCNR[1]='British Pound Sterling-GBP';curFCNR[1]='GBP';
conFCNR[2]='Euro-EUR';curFCNR[2]='EUR';
conFCNR[3]='Japanese Yen-JPY';curFCNR[3]='JPY';
conFCNR[4]='Canadian Dollar-CAD';curFCNR[4]='CAD';
conFCNR[5]='Australian Dollar-AUD';curFCNR[5]='AUD';
conFCNR[6]='Hong Kong Dollar-HKD';curFCNR[6]='HKD';
conFCNR[7]='Singapore Dollar-SGD';curFCNR[7]='SGD';


function populateCurrencyFCNR(currencyId){
	var currencyId = document.getElementById(currencyId);
	
	for (var i=0; i<curFCNR.length; i++) {
		currencyId.options[currencyId.length] = new Option(conFCNR[i],curFCNR[i]);
	}
	
}
	
function populateCurrencyEditFCNR(currencyId, selectedValue){
	var currencyId = document.getElementById(currencyId);
	
	//currencyId.selectedIndex = curFCNR.indexOf(selectedValue);
	//currencyId.options[0] = new Option(selectedValue,selectedValue);
	
	for (var i=0; i<curFCNR.length; i++) {
		currencyId.options[currencyId.length] = new Option(conFCNR[i],curFCNR[i]);
	}
	//currencyId.selectedIndex = curFCNR.indexOf(selectedValue);
	$("#currency").val(selectedValue).change();
}
//-----------------------------------------------------------------------------



//-----------------------------------RFC---------------------------------------
var conRFC=new Array();
var curRFC=new Array();
conRFC[0]='United States Dollar-USD';curRFC[0]='USD';
conRFC[1]='British Pound Sterling-GBP';curRFC[1]='GBP';
conRFC[2]='Euro-EUR';curRFC[2]='EUR';
conRFC[3]='Japanese Yen-JPY';curRFC[3]='JPY';
conRFC[4]='Canadian Dollar-CAD';curRFC[4]='CAD';
conRFC[5]='Australian Dollar-AUD';curRFC[5]='AUD';


function populateCurrencyRFC(currencyId){
	var currencyId = document.getElementById(currencyId);
	
	for (var i=0; i<curRFC.length; i++) {
		currencyId.options[currencyId.length] = new Option(conRFC[i],curRFC[i]);
	}
}
	
function populateCurrencyEditRFC(currencyId, selectedValue){
	var currencyId = document.getElementById(currencyId);
	
//	currencyId.selectedIndex = curRFC.indexOf(selectedValue);
//	currencyId.options[0] = new Option(selectedValue,selectedValue);
	
	for (var i=0; i<curRFC.length; i++) {
		currencyId.options[currencyId.length] = new Option(conRFC[i],curRFC[i]);
	}
	//currencyId.selectedIndex = curRFC.indexOf(selectedValue);
	$("#currency").val(selectedValue).change();
}
//-----------------------------------------------------------------------------