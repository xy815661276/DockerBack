(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-a90bbba4"],{"11e9":function(t,e,n){var a=n("52a7"),r=n("4630"),i=n("6821"),o=n("6a99"),c=n("69a8"),s=n("c69a"),u=Object.getOwnPropertyDescriptor;e.f=n("9e1e")?u:function(t,e){if(t=i(t),e=o(e,!0),s)try{return u(t,e)}catch(n){}if(c(t,e))return r(!a.f.call(t,e),t[e])}},"15bd":function(t,e,n){},1799:function(t,e,n){"use strict";var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("nav",{attrs:{"aria-label":"Page navigation example"}},[t.pages>1?n("ul",{staticClass:"pagination"},[n("router-link",{staticClass:"page-item previous",style:{visibility:1===t.current?"hidden":"visible"},attrs:{tag:"a",to:"/"+t.classification+"/page/"+(t.current-1)},nativeOn:{click:function(e){return t.callParentGetPage(t.current-1)}}},[n("span",{attrs:{"aria-hidden":"true"}},[t._v("« Prev")])]),n("router-link",{staticClass:"page-item next",style:{visibility:t.current===t.pages?"hidden":"visible"},attrs:{tag:"a",to:"/"+t.classification+"/page/"+(t.current+1)},nativeOn:{click:function(e){return t.callParentGetPage(t.current+1)}}},[n("span",{attrs:{"aria-hidden":"true"}},[t._v("Next »")])])],1):t._e()])},r=[],i=(n("c5f6"),{name:"Pagination",props:{pages:{type:Number,default:0},classification:{type:String,default:""}},data:function(){return{loading:!0,loaded:!1,errored:!1,current:1}},created:function(){this.pages>0&&(this.current=1)},methods:{callParentGetPage:function(t){this.$parent.getSpecificPage(t)}}}),o=i,c=(n("8cdd"),n("2877")),s=Object(c["a"])(o,a,r,!1,null,"382f8336",null);e["a"]=s.exports},1931:function(t,e,n){"use strict";var a=n("9eb6"),r=n.n(a);r.a},"5dbc":function(t,e,n){var a=n("d3f4"),r=n("8b97").set;t.exports=function(t,e,n){var i,o=e.constructor;return o!==n&&"function"==typeof o&&(i=o.prototype)!==n.prototype&&a(i)&&r&&r(t,i),t}},"8b97":function(t,e,n){var a=n("d3f4"),r=n("cb7c"),i=function(t,e){if(r(t),!a(e)&&null!==e)throw TypeError(e+": can't set as prototype!")};t.exports={set:Object.setPrototypeOf||("__proto__"in{}?function(t,e,a){try{a=n("9b43")(Function.call,n("11e9").f(Object.prototype,"__proto__").set,2),a(t,[]),e=!(t instanceof Array)}catch(r){e=!0}return function(t,n){return i(t,n),e?t.__proto__=n:a(t,n),t}}({},!1):void 0),check:i}},"8cdd":function(t,e,n){"use strict";var a=n("15bd"),r=n.n(a);r.a},9093:function(t,e,n){var a=n("ce10"),r=n("e11e").concat("length","prototype");e.f=Object.getOwnPropertyNames||function(t){return a(t,r)}},"9eb6":function(t,e,n){},a70e:function(t,e,n){"use strict";n.r(e);var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return t.loaded?n("div",[n("div",{staticClass:"container"},[t._l(t.projectslist,(function(e,a){return n("div",{key:a},[n("ProjectCard",t._b({},"ProjectCard",e,!1))],1)})),n("Pagination",{ref:"son",attrs:{pages:t.pages,classification:t.key}})],2)]):t._e()},r=[],i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"row no-gutters position-relative border-bottom shadow my-4"},[n("div",{staticClass:"col-md-3 mb-md-0 p-md-4"},[t.imgstatus?n("img",{staticClass:"w-100",attrs:{src:t.img,alt:""},on:{error:t.imgError}}):n("span",{staticClass:"fa fa-image fa-5x"})]),n("div",{staticClass:"col-md-9 position-static p-4 pl-md-0"},[n("h5",{staticClass:"mt-0"},[t._v(t._s(t.title))]),n("p",{staticClass:"introduction",domProps:{innerHTML:t._s(t.compiledMarkdown)}}),n("div",{staticClass:"bottom"},[n("router-link",{attrs:{tag:"a",to:"/post/"+t.link},nativeOn:{click:function(e){return t.sendPdfLink(t.pdflink)}}},[t._v("Learn more")]),""!==t.time?n("span",{staticClass:"fa fa-calendar-minus-o"},[t._v(" "+t._s(t.time))]):t._e()],1)])])},o=[],c=n("0e54"),s=n.n(c),u={name:"ProjectCard",props:{img:{type:String,default:""},title:{type:String,default:"The title of the project"},intro:{type:String,default:"'The excerpt of the project's content'"},link:{type:String,default:""},time:{type:String,default:""},pdflink:{type:String,default:""}},data:function(){return{imgstatus:!0}},computed:{compiledMarkdown:function(){return s()(this.intro,{sanitize:!1})}},methods:{imgError:function(){this.imgstatus=!1},sendPdfLink:function(t){this.$store.commit("newProjectPdfLink",t)}}},l=u,f=(n("1931"),n("2877")),p=Object(f["a"])(l,i,o,!1,null,"a4600c8e",null),d=p.exports,g=n("1799"),h={name:"ProjectList",components:{ProjectCard:d,Pagination:g["a"]},data:function(){return{key:"projects",loading:!0,loaded:!1,errored:!1,projectslist:[],pages:0}},created:function(){var t=this;this.axios.get("http://cugclass.club/projects/page/1").then((function(e){t.projectslist=e.data.body,t.pages=e.data.data,t.loaded=!0})).catch((function(e){console.log(e),t.errored=!0})).finally((function(){t.loading=!1,window.scrollTo(0,0)}))},methods:{getSpecificPage:function(t){var e=this;t>=1&&t<=this.pages&&this.axios.get("http://cugclass.club/projects/page/"+t).then((function(n){e.projectslist=n.data.body,e.pages=n.data.data,e.loaded=!0,e.$refs.son.current=t})).catch((function(t){console.log(t),e.errored=!0})).finally((function(){e.loading=!1,window.scrollTo(0,0)}))}}},m=h,v=Object(f["a"])(m,a,r,!1,null,"a7dc0120",null);e["default"]=v.exports},aa77:function(t,e,n){var a=n("5ca1"),r=n("be13"),i=n("79e5"),o=n("fdef"),c="["+o+"]",s="​",u=RegExp("^"+c+c+"*"),l=RegExp(c+c+"*$"),f=function(t,e,n){var r={},c=i((function(){return!!o[t]()||s[t]()!=s})),u=r[t]=c?e(p):o[t];n&&(r[n]=u),a(a.P+a.F*c,"String",r)},p=f.trim=function(t,e){return t=String(r(t)),1&e&&(t=t.replace(u,"")),2&e&&(t=t.replace(l,"")),t};t.exports=f},c5f6:function(t,e,n){"use strict";var a=n("7726"),r=n("69a8"),i=n("2d95"),o=n("5dbc"),c=n("6a99"),s=n("79e5"),u=n("9093").f,l=n("11e9").f,f=n("86cc").f,p=n("aa77").trim,d="Number",g=a[d],h=g,m=g.prototype,v=i(n("2aeb")(m))==d,b="trim"in String.prototype,_=function(t){var e=c(t,!1);if("string"==typeof e&&e.length>2){e=b?e.trim():p(e,3);var n,a,r,i=e.charCodeAt(0);if(43===i||45===i){if(n=e.charCodeAt(2),88===n||120===n)return NaN}else if(48===i){switch(e.charCodeAt(1)){case 66:case 98:a=2,r=49;break;case 79:case 111:a=8,r=55;break;default:return+e}for(var o,s=e.slice(2),u=0,l=s.length;u<l;u++)if(o=s.charCodeAt(u),o<48||o>r)return NaN;return parseInt(s,a)}}return+e};if(!g(" 0o1")||!g("0b1")||g("+0x1")){g=function(t){var e=arguments.length<1?0:t,n=this;return n instanceof g&&(v?s((function(){m.valueOf.call(n)})):i(n)!=d)?o(new h(_(e)),n,g):_(e)};for(var y,P=n("9e1e")?u(h):"MAX_VALUE,MIN_VALUE,NaN,NEGATIVE_INFINITY,POSITIVE_INFINITY,EPSILON,isFinite,isInteger,isNaN,isSafeInteger,MAX_SAFE_INTEGER,MIN_SAFE_INTEGER,parseFloat,parseInt,isInteger".split(","),k=0;P.length>k;k++)r(h,y=P[k])&&!r(g,y)&&f(g,y,l(h,y));g.prototype=m,m.constructor=g,n("2aba")(a,d,g)}},fdef:function(t,e){t.exports="\t\n\v\f\r   ᠎             　\u2028\u2029\ufeff"}}]);