$wnd.showcase.runAsyncCallback24("function Ueb(a){this.a=a}\nfunction Web(a){this.a=a}\nfunction Yeb(a){this.a=a}\nfunction bfb(a,b){this.a=a;this.b=b}\nfunction bJb(a,b){WIb(a,b);Op(a.hb,b)}\nfunction Op(a,b){a.remove(b)}\nfunction txb(){var a;if(!qxb||wxb()){a=new y4b;vxb(a);qxb=a}return qxb}\nfunction wxb(){var a=$doc.cookie;if(a!=rxb){rxb=a;return true}else{return false}}\nfunction xxb(a){sxb&&(a=encodeURIComponent(a));$doc.cookie=a+'=;expires=Fri, 02-Jan-1970 00:00:00 GMT'}\nfunction Reb(a){var b,c,d,e;if(a.c.hb.options.length<1){hLb(a.a,'');hLb(a.b,'');return}d=a.c.hb.selectedIndex;b=ZIb(a.c,d);c=(e=txb(),UC(e.ah(b)));hLb(a.a,b);hLb(a.b,c)}\nfunction Qeb(a,b){var c,d,e,f;Np(a.c.hb);f=0;e=new h1b(txb());for(d=g1b(e);d.a.Vf();){c=UC(m1b(d));$Ib(a.c,c);xZb(c,b)&&(f=a.c.hb.options.length-1)}xm((qm(),pm),new bfb(a,f))}\nfunction vxb(b){var c=$doc.cookie;if(c&&c!=''){var d=c.split('; ');for(var e=0;e<d.length;++e){var f,g;var h=d[e].indexOf('=');if(h==-1){f=d[e];g=''}else{f=d[e].substring(0,h);g=d[e].substring(h+1)}if(sxb){try{f=decodeURIComponent(f)}catch(a){}try{g=decodeURIComponent(g)}catch(a){}}b.bh(f,g)}}}\nfunction Peb(a){var b,c,d;c=new bHb(3,3);a.c=new dJb;b=new AAb('Delete');yh(b.hb,ydc,true);vGb(c,0,0,'<b><b>Existing Cookies:<\\/b><\\/b>');yGb(c,0,1,a.c);yGb(c,0,2,b);a.a=new rLb;vGb(c,1,0,'<b><b>Name:<\\/b><\\/b>');yGb(c,1,1,a.a);a.b=new rLb;d=new AAb('Set Cookie');yh(d.hb,ydc,true);vGb(c,2,0,'<b><b>Value:<\\/b><\\/b>');yGb(c,2,1,a.b);yGb(c,2,2,d);Fh(d,new Ueb(a),(Uu(),Uu(),Tu));Fh(a.c,new Web(a),(Nu(),Nu(),Mu));Fh(b,new Yeb(a),(null,Tu));Qeb(a,null);return c}\nSX(443,1,nac,Ueb);_.Oc=function Veb(a){var b,c,d;c=ep(this.a.a.hb,Ncc);d=ep(this.a.b.hb,Ncc);b=new mC(oY(sY((new kC).q.getTime()),{l:2513920,m:20,h:0}));if(c.length<1){xyb('You must specify a cookie name');return}yxb(c,d,b);Qeb(this.a,c)};var FN=zYb(Aac,'CwCookies/1',443);SX(444,1,oac,Web);_.Nc=function Xeb(a){Reb(this.a)};var GN=zYb(Aac,'CwCookies/2',444);SX(445,1,nac,Yeb);_.Oc=function Zeb(a){var b,c;c=this.a.c.hb.selectedIndex;if(c>-1&&c<this.a.c.hb.options.length){b=ZIb(this.a.c,c);xxb(b);bJb(this.a.c,c);Reb(this.a)}};var HN=zYb(Aac,'CwCookies/3',445);SX(446,1,wac);_.xc=function afb(){j_(this.b,Peb(this.a))};SX(447,1,{},bfb);_.zc=function cfb(){this.b<this.a.c.hb.options.length&&cJb(this.a.c,this.b);Reb(this.a)};_.b=0;var JN=zYb(Aac,'CwCookies/5',447);var qxb=null,rxb;z7b(Cl)(24);\n//# sourceURL=showcase-24.js\n")
