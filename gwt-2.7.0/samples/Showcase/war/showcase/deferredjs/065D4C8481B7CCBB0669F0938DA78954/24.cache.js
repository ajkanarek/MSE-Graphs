$wnd.showcase.runAsyncCallback24("function CVb(a){this.a=a}\nfunction EVb(a){this.a=a}\nfunction GVb(a){this.a=a}\nfunction LVb(a,b){this.a=a;this.b=b}\nfunction Vp(a,b){a.remove(b)}\nfunction Onc(a,b){Gnc(a,b);Vp((Ubc(),a.hb),b)}\nfunction Lbc(){var a;if(!Ibc||Obc()){a=new pLc;Nbc(a);Ibc=a}return Ibc}\nfunction Obc(){var a=$doc.cookie;if(a!=Jbc){Jbc=a;return true}else{return false}}\nfunction Pbc(a){Kbc&&(a=encodeURIComponent(a));$doc.cookie=a+'=;expires=Fri, 02-Jan-1970 00:00:00 GMT'}\nfunction zVb(a){var b,c,d,e;if(Jnc(a.c).options.length<1){Ypc(a.a,'');Ypc(a.b,'');return}d=Jnc(a.c).selectedIndex;b=Knc(a.c,d);c=(e=Lbc(),Qfb(e.Wh(b)));Ypc(a.a,b);Ypc(a.b,c)}\nfunction yVb(a,b){var c,d,e,f;Up(Jnc(a.c));f=0;e=new $Hc(Lbc());for(d=ZHc(e);d.a.Ng();){c=Qfb(dIc(d));Lnc(a.c,c);oEc(c,b)&&(f=Jnc(a.c).options.length-1)}Em((xm(),wm),new LVb(a,f))}\nfunction Nbc(b){var c=$doc.cookie;if(c&&c!=''){var d=c.split('; ');for(var e=0;e<d.length;++e){var f,g;var h=d[e].indexOf('=');if(h==-1){f=d[e];g=''}else{f=d[e].substring(0,h);g=d[e].substring(h+1)}if(Kbc){try{f=decodeURIComponent(f)}catch(a){}try{g=decodeURIComponent(g)}catch(a){}}b.Xh(f,g)}}}\nfunction xVb(a){var b,c,d;c=new Mlc(3,3);a.c=new Qnc;b=new dfc('Delete');Dh((Ubc(),b.hb),mVc,true);clc(c,0,0,'<b><b>Existing Cookies:<\\/b><\\/b>');flc(c,0,1,a.c);flc(c,0,2,b);a.a=new gqc;clc(c,1,0,'<b><b>Name:<\\/b><\\/b>');flc(c,1,1,a.a);a.b=new gqc;d=new dfc('Set Cookie');Dh(d.hb,mVc,true);clc(c,2,0,'<b><b>Value:<\\/b><\\/b>');flc(c,2,1,a.b);flc(c,2,2,d);Kh(d,new CVb(a),(Ru(),Ru(),Qu));Kh(a.c,new EVb(a),(Ku(),Ku(),Ju));Kh(b,new GVb(a),(null,Qu));yVb(a,null);return c}\nECb(472,1,IRc,CVb);_.Oc=function DVb(a){var b,c,d;c=lp(dh(this.a.a),BUc);d=lp(dh(this.a.b),BUc);b=new ifb(aDb(eDb((new gfb).q.getTime()),{l:2513920,m:20,h:0}));if(c.length<1){Tcc('You must specify a cookie name');return}Qbc(c,d,b);yVb(this.a,c)};var ssb=qDc(VRc,'CwCookies/1',472);ECb(473,1,JRc,EVb);_.Nc=function FVb(a){zVb(this.a)};var tsb=qDc(VRc,'CwCookies/2',473);ECb(474,1,IRc,GVb);_.Oc=function HVb(a){var b,c;c=Jnc(this.a.c).selectedIndex;if(c>-1&&c<Jnc(this.a.c).options.length){b=Knc(this.a.c,c);Pbc(b);Onc(this.a.c,c);zVb(this.a)}};var usb=qDc(VRc,'CwCookies/3',474);ECb(475,1,RRc);_.xc=function KVb(){TFb(this.b,xVb(this.a))};ECb(476,1,{},LVb);_.zc=function MVb(){this.b<Jnc(this.a.c).options.length&&Pnc(this.a.c,this.b);zVb(this.a)};_.b=0;var wsb=qDc(VRc,'CwCookies/5',476);var Ibc=null,Jbc;qOc(Jl)(24);\n//# sourceURL=showcase-24.js\n")
