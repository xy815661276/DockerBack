(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-36f10da5"],{"11e9":function(t,e,a){var i=a("52a7"),n=a("4630"),r=a("6821"),o=a("6a99"),s=a("69a8"),c=a("c69a"),l=Object.getOwnPropertyDescriptor;e.f=a("9e1e")?l:function(t,e){if(t=r(t),e=o(e,!0),c)try{return l(t,e)}catch(a){}if(s(t,e))return n(!i.f.call(t,e),t[e])}},1799:function(t,e,a){"use strict";var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("nav",{attrs:{"aria-label":"Page navigation example"}},[t.pages>1?a("ul",{staticClass:"pagination"},[a("router-link",{staticClass:"page-item previous",style:{visibility:1===t.current?"hidden":"visible"},attrs:{tag:"a",to:"/"+t.classification+"/page/"+(t.current-1)},nativeOn:{click:function(e){return t.getSpecificPage(t.current-1)}}},[a("span",{attrs:{"aria-hidden":"true"}},[t._v("« Prev")])]),a("router-link",{staticClass:"page-item next",style:{visibility:t.current===t.pages?"hidden":"visible"},attrs:{tag:"a",to:"/"+t.classification+"/page/"+(t.current+1)},nativeOn:{click:function(e){return t.getSpecificPage(t.current+1)}}},[a("span",{attrs:{"aria-hidden":"true"}},[t._v("Next »")])])],1):t._e()])},n=[],r=(a("c5f6"),{name:"Pagination",props:{pages:{type:Number,default:8},classification:{type:String,default:""}},data:function(){return{loading:!0,loaded:!1,errored:!1,current:1}},methods:{getSpecificPage:function(t){var e=this;t>=1&&t<=this.pages&&(this.axios.get().then((function(a){e.blogs=a.data.body,e.loaded=!0,e.current=t,console.log(e.blogs)})).catch((function(t){console.log(t),e.errored=!0})).finally((function(){return e.loading=!1})),this.current=t)}}}),o=r,s=(a("e6cd"),a("2877")),c=Object(s["a"])(o,i,n,!1,null,"0d8e965c",null);e["a"]=c.exports},5222:function(t,e,a){},"5dbc":function(t,e,a){var i=a("d3f4"),n=a("8b97").set;t.exports=function(t,e,a){var r,o=e.constructor;return o!==a&&"function"==typeof o&&(r=o.prototype)!==a.prototype&&i(r)&&n&&n(t,r),t}},"68f0":function(t,e,a){},"8b97":function(t,e,a){var i=a("d3f4"),n=a("cb7c"),r=function(t,e){if(n(t),!i(e)&&null!==e)throw TypeError(e+": can't set as prototype!")};t.exports={set:Object.setPrototypeOf||("__proto__"in{}?function(t,e,i){try{i=a("9b43")(Function.call,a("11e9").f(Object.prototype,"__proto__").set,2),i(t,[]),e=!(t instanceof Array)}catch(n){e=!0}return function(t,a){return r(t,a),e?t.__proto__=a:i(t,a),t}}({},!1):void 0),check:r}},9093:function(t,e,a){var i=a("ce10"),n=a("e11e").concat("length","prototype");e.f=Object.getOwnPropertyNames||function(t){return i(t,n)}},a27e:function(t,e,a){"use strict";var i=function(){var t=this,e=t.$createElement;t._self._c;return t._m(0)},n=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"col-md-3 p-md-4"},[a("h2",{staticClass:"my-4"},[t._v("CATEGORY")]),a("div",{staticClass:"list-group"},[a("a",{staticClass:"list-group-item ",attrs:{href:"performance.html"}},[t._v("performance "),a("span",[t._v("(3)")])]),a("a",{staticClass:"list-group-item",attrs:{href:"realization.html"}},[t._v("realization "),a("span",[t._v("(4)")])]),a("a",{staticClass:"list-group-item",attrs:{href:"networks.html"}},[t._v("networks "),a("span",[t._v("(5)")])]),a("a",{staticClass:"list-group-item active",attrs:{href:"container_io.html"}},[t._v("container I/O "),a("span",[t._v("(5)")])]),a("a",{staticClass:"list-group-item",attrs:{href:"security_isolation.html"}},[t._v("security isolation "),a("span",[t._v("(3)")])]),a("a",{staticClass:"list-group-item",attrs:{href:"#"}},[t._v("image management "),a("span",[t._v("(16)")])]),a("a",{staticClass:"list-group-item",attrs:{href:"others.html"}},[t._v("others "),a("span",[t._v("(2)")])])])])}],r={name:"Categories"},o=r,s=(a("eaa4"),a("2877")),c=Object(s["a"])(o,i,n,!1,null,"30d65a2e",null);e["a"]=c.exports},aa77:function(t,e,a){var i=a("5ca1"),n=a("be13"),r=a("79e5"),o=a("fdef"),s="["+o+"]",c="​",l=RegExp("^"+s+s+"*"),d=RegExp(s+s+"*$"),f=function(t,e,a){var n={},s=r((function(){return!!o[t]()||c[t]()!=c})),l=n[t]=s?e(p):o[t];a&&(n[a]=l),i(i.P+i.F*s,"String",n)},p=f.trim=function(t,e){return t=String(n(t)),1&e&&(t=t.replace(l,"")),2&e&&(t=t.replace(d,"")),t};t.exports=f},c5f6:function(t,e,a){"use strict";var i=a("7726"),n=a("69a8"),r=a("2d95"),o=a("5dbc"),s=a("6a99"),c=a("79e5"),l=a("9093").f,d=a("11e9").f,f=a("86cc").f,p=a("aa77").trim,u="Number",m=i[u],v=m,g=m.prototype,h=r(a("2aeb")(g))==u,_="trim"in String.prototype,C=function(t){var e=s(t,!1);if("string"==typeof e&&e.length>2){e=_?e.trim():p(e,3);var a,i,n,r=e.charCodeAt(0);if(43===r||45===r){if(a=e.charCodeAt(2),88===a||120===a)return NaN}else if(48===r){switch(e.charCodeAt(1)){case 66:case 98:i=2,n=49;break;case 79:case 111:i=8,n=55;break;default:return+e}for(var o,c=e.slice(2),l=0,d=c.length;l<d;l++)if(o=c.charCodeAt(l),o<48||o>n)return NaN;return parseInt(c,i)}}return+e};if(!m(" 0o1")||!m("0b1")||m("+0x1")){m=function(t){var e=arguments.length<1?0:t,a=this;return a instanceof m&&(h?c((function(){g.valueOf.call(a)})):r(a)!=u)?o(new v(C(e)),a,m):C(e)};for(var b,y=a("9e1e")?l(v):"MAX_VALUE,MIN_VALUE,NaN,NEGATIVE_INFINITY,POSITIVE_INFINITY,EPSILON,isFinite,isInteger,isNaN,isSafeInteger,MAX_SAFE_INTEGER,MIN_SAFE_INTEGER,parseFloat,parseInt,isInteger".split(","),w=0;y.length>w;w++)n(v,b=y[w])&&!n(m,b)&&f(m,b,d(v,b));m.prototype=g,g.constructor=m,a("2aba")(i,u,m)}},e6cd:function(t,e,a){"use strict";var i=a("5222"),n=a.n(i);n.a},eaa4:function(t,e,a){"use strict";var i=a("68f0"),n=a.n(i);n.a},fd3f:function(t,e,a){"use strict";a.r(e);var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("Header"),a("div",{staticClass:"container"},[t.loaded?a("div",[t._l(t.blogs,(function(e,i){return a("article",{key:i},[a("BlogCard",t._b({},"BlogCard",e,!1))],1)})),a("Pagination",{attrs:{pages:t.pages,classification:t.key}})],2):t._e(),t._m(0),t._m(1),t._m(2),t._m(3),t._m(4),t._m(5),a("Pagination",{attrs:{pages:t.pages,classification:t.key}})],1)],1)},n=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"row no-gutters position-relative border-bottom my-4"},[a("div",{staticClass:"col-md-3 mb-md-0 p-md-4"},[a("img",{staticClass:"w-100",attrs:{src:"images/FastBuild.png",alt:"#"}})]),a("div",{staticClass:"col-md-9 position-static p-4 pl-md-0"},[a("h5",{staticClass:"mt-0"},[t._v("FastBuild: Accelerating Docker Image Building for Efficient Development and\n                    Deployment of Container")]),a("p",[t._v("We implemented FastBuild to accelerate docker image building.\n                    FastBuild maintains a local file cache to\n                    minimize the expensive file downloading. To further accelerate the\n                    image building, FastBuild overlaps operations of instructions' execution,\n                    and writing intermediate image layers to the disk.")]),a("a",{attrs:{href:"abstract/abstract_1.html"}},[t._v("Learn more")])])])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"row no-gutters position-relative border-bottom my-4"},[a("div",{staticClass:"col-md-3 mb-md-0 p-md-4"},[a("img",{staticClass:"w-100",attrs:{src:"images/AdaptiveResourceViewsforContainers.png",alt:"#"}})]),a("div",{staticClass:"col-md-9 position-static p-4 pl-md-0"},[a("h5",{staticClass:"mt-0"},[t._v("Adaptive Resource Views for Containers")]),a("p",[t._v("We develop a per-container view of resources to\n                    export information on the actual resource allocation to containerized\n                    applications. The central design of the resource view is a per-\n                    container "),a("code",[t._v("sys_namespace")]),t._v(" that calculates the effective capacity of\n                    CPU and memory in the presence of resource sharing among containers.\n                    We further create a virtual "),a("code",[t._v("sysfs")]),t._v(" to seamlessly interface\n                    user space applications with "),a("code",[t._v("sys_namespace")]),t._v(".")]),a("a",{attrs:{href:"abstract/abstract_2.html"}},[t._v("Learn more")])])])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"row no-gutters position-relative border-bottom my-4"},[a("div",{staticClass:"col-md-3 mb-md-0 p-md-4"},[a("img",{staticClass:"w-100",attrs:{src:"images/CNTC.png",alt:"#"}})]),a("div",{staticClass:"col-md-9 position-static p-4 pl-md-0"},[a("h5",{staticClass:"mt-0"},[t._v("CNTC: A Container Aware Network Traffic Control Framework")]),a("p",[t._v("We propose a Container Network Traffic Control ("),a("i",[t._v("CNTC")]),t._v(")\n                    framework which can provide strong isolation and container-level management\n                    for network resource with joint consideration of container characteristics\n                    and quality of service. To simplify the traffic control, we also design a\n                    series of APIs which allow inexpert programmers to perform complicated\n                    traffic control on each container.")]),a("a",{attrs:{href:"abstract/abstract_3.html"}},[t._v("Learn more")])])])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"row no-gutters position-relative border-bottom my-4"},[a("div",{staticClass:"col-md-3 mb-md-0 p-md-4"},[a("img",{staticClass:"w-100",attrs:{src:"images/Container-BasedCustomizationApproachforMobileEnvironmentsonClouds.png",alt:"#"}})]),a("div",{staticClass:"col-md-9 position-static p-4 pl-md-0"},[a("h5",{staticClass:"mt-0"},[t._v("Container-Based Customization Approach for Mobile Environments on Clouds")]),a("p",[t._v("When building a mobile cloud platform ("),a("i",[t._v("MCP")]),t._v("), one of\n                    the most important things is to provide an execution environment\n                    for mobile applications. we propose a unified and effective\n                    approach for customizing Android environments on clouds. The approach\n                    provides a container-based solution to custom-tailor Android OS\n                    components, as well as a way to run Android applications for different\n                    scenarios.")]),a("a",{attrs:{href:"abstract/abstract_4.html"}},[t._v("Learn more")])])])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"row no-gutters position-relative border-bottom my-4"},[a("div",{staticClass:"col-md-3 mb-md-0 p-md-4"},[a("img",{staticClass:"w-100",attrs:{src:"images/Container-BasedCloudPlatformforMobileComputationOfﬂoading.png",alt:"#"}})]),a("div",{staticClass:"col-md-9 position-static p-4 pl-md-0"},[a("h5",{staticClass:"mt-0"},[t._v("Container-Based Cloud Platform for Mobile Computation Offloading")]),a("p",[t._v("Many efforts have been made to improve the performance\n                    and reduce energy consumption of mobile devices by offloading\n                    computational codes to the cloud. We analyze the characteristics of typical\n                    offloading workloads and design our platform solution accordingly, named Rattrap.\n                    Rattrap develops a new runtime environment, Cloud Android\n                    Container, for mobile computation offloading, replacing heavy-\n                    weight virtual machines ("),a("i",[t._v("VMs")]),t._v(").Our design exploits the idea\n                    of running operating systems with differential kernel features\n                    inside containers with driver extensions, which partially breaks\n                    the limitation of OS-level virtualization.")]),a("a",{attrs:{href:"abstract/abstract_5.html"}},[t._v("Learn more")])])])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"row no-gutters position-relative border-bottom my-4"},[a("div",{staticClass:"col-md-3 mb-md-0 p-md-4"},[a("img",{staticClass:"w-100",attrs:{src:"images/APerformanceStudyofContainersinCloudEnvironment.png",alt:"#"}})]),a("div",{staticClass:"col-md-9 position-static p-4 pl-md-0"},[a("h5",{staticClass:"mt-0"},[t._v("A Performance Study of Containers in Cloud Environment")]),a("p",[t._v("In this paper, we carry\n                    out a performance study to explore the appropriate way to use containers\n                    from different perspectives. We first conduct a series of experiments to\n                    measure performance differences between application containers and\n                    system containers, then evaluate the overhead of extra virtual machine layer\n                    between the bare metal and containers, and finally inspect the service\n                    quality of ECS ("),a("i",[t._v("Amazon EC2 Container Service")]),t._v(") and GKE ("),a("i",[t._v("Google\n                        Container Engine")]),t._v(").")]),a("a",{attrs:{href:"abstract/abstract_6.html"}},[t._v("Learn more")])])])}],r=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("header",{staticClass:"bg-secondary text-white",style:{backgroundImage:"url("+t.bgimgurl+")"}},[a("div",{staticClass:"container text-center"},[a("h1",[t._v(t._s(t.title))]),a("p",{staticClass:"lead"},[t._v(t._s(t.description))])])])},o=[],s={name:"Header",props:{title:{type:String,default:"BLOG"},description:{type:String,default:"Blog of Our Research Team"},bgimgurl:{type:String,default:""}}},c=s,l=a("2877"),d=Object(l["a"])(c,r,o,!1,null,"38af1c08",null),f=d.exports,p=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"card mb-4"},[a("img",{staticClass:"card-img-top",attrs:{src:t.img,alt:"Card image cap"}}),a("div",{staticClass:"card-body"},[a("h2",{staticClass:"card-title"},[t._v(t._s(t.title))]),a("p",{staticClass:"card-text"},[t._v(t._s(t.intro))]),a("a",{staticClass:"btn btn-primary",attrs:{href:t.link}},[t._v("Read More →")])]),a("div",{staticClass:"card-footer text-muted"},[t._v("\n        Posted on "+t._s(t.time)+" by "+t._s(t.author)+"\n        "),a("a",{attrs:{href:"#"}},[t._v("Start Bootstrap")])])])},u=[],m={name:"BlogCard",props:["author","id","img","intro","link","time","title"]},v=m,g=Object(l["a"])(v,p,u,!1,null,"b2bca5ac",null),h=g.exports,_=a("1799"),C=a("a27e"),b={name:"Blog",components:{Header:f,Pagination:_["a"],BlogCard:h,Categories:C["a"]},data:function(){return{key:"blog",loading:!0,loaded:!1,errored:!1,blogs:[],pages:8}},created:{function:function(){var t=this;this.axios.get().then((function(e){t.blogs=e.data.body,t.loaded=!0,console.log(t.blogs)})).catch((function(e){console.log(e),t.errored=!0})).finally((function(){return t.loading=!1}))}}},y=b,w=Object(l["a"])(y,i,n,!1,null,"1c030908",null);e["default"]=w.exports},fdef:function(t,e){t.exports="\t\n\v\f\r   ᠎             　\u2028\u2029\ufeff"}}]);
//# sourceMappingURL=chunk-36f10da5.af0e9d8b.js.map