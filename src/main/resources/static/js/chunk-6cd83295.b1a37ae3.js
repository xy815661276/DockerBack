(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6cd83295"],{"53e7":function(t,e,r){"use strict";var a=r("9724"),n=r.n(a);n.a},"7f0a":function(t,e,r){"use strict";var a=r("ab2d"),n=r.n(a);n.a},"8f58":function(t,e,r){},9724:function(t,e,r){},ab2d:function(t,e,r){},c6c4:function(t,e,r){"use strict";r.r(e);var a=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",[r("Header"),r("CommunityList")],1)},n=[],o=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("header",{staticClass:"bg-secondary text-white",style:{backgroundImage:"url("+t.bgimgurl+")"}},[r("div",{staticClass:"container text-center"},[r("h1",[t._v(t._s(t.title))]),r("p",{staticClass:"lead"},[t._v(t._s(t.description))])])])},s=[],i={name:"Header",props:{title:{type:String,default:"Related Work"},description:{type:String,default:"Other Communities Related to Container"},bgimgurl:{type:String,default:""}}},c=i,l=(r("7f0a"),r("2877")),u=Object(l["a"])(c,o,s,!1,null,"0d29994f",null),d=u.exports,m=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"container"},[r("div",{staticClass:"row text-center"},t._l(t.cards,(function(t,e){return r("div",{key:e,staticClass:"col-sm-6 col-md-4 col-lg-3 mb-5"},[r("CommunityCard",{attrs:{card:t}})],1)})),0)])},p=[],g=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"card h-100 shadow"},[r("a",{attrs:{href:t.card.url,title:t.card.title,target:"_blank"}},[r("img",{staticClass:"p-3",attrs:{src:t.card.logourl,alt:t.card.title}})]),r("div",{staticClass:"card-body"},[r("h3",{staticClass:"card-title"},[r("a",{attrs:{href:t.card.url,title:t.card.title,target:"_blank"}},[t._v(t._s(t.card.title))])]),r("p",{staticClass:"card-text"},[t._v(t._s(t.card.intro))])])])},h=[],f={name:"CommunityCard",props:["card"]},v=f,b=(r("53e7"),Object(l["a"])(v,g,h,!1,null,"2b1d7c23",null)),y=b.exports,C={name:"OtherCommunity",components:{CommunityCard:y},data:function(){return{cards:[{title:"moby",url:"https://mobyproject.org/",logourl:"https://avatars3.githubusercontent.com/u/27259197?s=200&v=4",intro:"Moby is an open framework created by Docker to assemble specialized container systems without reinventing the wheel"},{title:"Kubernetes",url:"https://kubernetes.io/",logourl:"https://avatars3.githubusercontent.com/u/13629408?s=200&v=4",intro:"Kubernetes is an open-source container-orchestration system for automating application deployment, scaling, and management."},{title:"containered",url:"https://containerd.io",logourl:"https://avatars2.githubusercontent.com/u/14037953?s=200&v=4",intro:"An industry-standard container runtime with an emphasis on simplicity, robustness and portability"},{title:"rkt",url:"https://coreos.com/rkt/",logourl:"https://avatars1.githubusercontent.com/u/5866990?s=200&v=4",intro:"rkt is a pod-native container engine for Linux. It is composable, secure, and built on standards."},{title:"gvisor",url:"https://gvisor.dev/",logourl:"https://github.com/google/gvisor/raw/master/g3doc/logo.png",intro:"A container sandbox runtime focused on security, efficiency, and ease of use."},{title:"CRI-O",url:"https://cri-o.io/",logourl:"https://github.com/cri-o/cri-o/raw/master/logo/crio-logo.svg?sanitize=true",intro:"Open Container Initiative-based implementation of Kubernetes Container Runtime Interface "},{title:"docker",url:"https://www.docker.com/",logourl:"https://avatars0.githubusercontent.com/u/5429470?s=200&v=4",intro:"Docker is a set of platform-as-a-service products that use OS-level virtualization to deliver software in packages called containers."},{title:"OCI",url:"https://www.opencontainers.org/",logourl:"https://avatars1.githubusercontent.com/u/12563465?s=200&v=4",intro:"Creating open standards around container technology"},{title:"mesos",url:"http://mesos.apache.org/",logourl:"https://avatars1.githubusercontent.com/u/229272?s=200&v=4",intro:"Apache Mesos abstracts CPU, memory, storage, and other compute resources away from machines."}]}}},w=C,k=Object(l["a"])(w,m,p,!1,null,"27228712",null),_=k.exports,x={name:"RelatedWork",components:{Header:d,CommunityList:_}},O=x,I=(r("f57f"),Object(l["a"])(O,a,n,!1,null,"f2df5ec8",null));e["default"]=I.exports},f57f:function(t,e,r){"use strict";var a=r("8f58"),n=r.n(a);n.a}}]);