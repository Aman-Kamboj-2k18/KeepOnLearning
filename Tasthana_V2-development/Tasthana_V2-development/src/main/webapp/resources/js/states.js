// states

var con=new Array();

//=========================
con[0]='Andhra Pradesh';
con[1]='Arunachal Pradesh';
con[2]=' Assam';
con[3]='Bihar';
con[4]=' Chhattisgarh';
con[5]='Goa';
con[6]='Gujarat';
con[7]='Haryana';
con[8]='Himachal Pradesh';
con[9]=' Jammu and Kashmir';
con[10]=' Jharkhand';
con[11]='Karnataka';
con[12]='Kerala';
con[13]='Madhya Pradesh';
con[14]='Maharashtra';
con[15]='Manipur ';
con[16]='Meghalaya';
con[17]='Mizoram';
con[18]='Nagaland';
con[19]='Odisha ';
con[20]='Punjab ';
con[21]='Rajasthan ';
con[22]='Sikkim ';
con[23]='Tamil Nadu';
con[24]='Telangana';
con[25]='Tripura';
con[26]='Uttar Pradesh';
con[27]='Uttarakhand ';
con[28]=' West Bengal';


function populateStates(stateId){
	var state = document.getElementById(stateId);
	for (var i=0; i<con.length; i++) {
		state.options[state.length] = new Option(con[i]);
	}

}
