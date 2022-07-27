# SpringSecurity源码学习

> 学习视频地址：https://www.bilibili.com/video/BV1Rv4y1w74n

## 一、第一节内容

主要讲解了SpringSecurity的顶层脉络以及几个核心的类：

* SecurityBuilder
* HttpSecurity
* WebSecurity
* SecurityFilterChain
* FilterChainProxy

SpringSecurity进入流程：
FilterChainProxy -> 根据请求决定一个 SecurityFilterChain -> 执行SecurityFilterChain中的一系列过滤器

## 二、第二节内容

主要讲解下面三个类：

* HttpSecurity 内幕
* SecurityConfigurer
* AbstractConfiguredSecurityBuilder

SecurityBuilder用来构建安全对象，安全对象包括：HttpSecurity、FilterChainProxy、AuthenticationManager
SecurityConfigurer用来配置安全对象构建器（SecurityBuilder），典型的有：FormLoginConfigurer、CsrfConfigurer等

## 三、第三节内容

主要讲解下面四个类：

* Authentication
* AuthenticationManager
* AuthenticationManagerBuilder
* ProviderManager
* AuthenticationProvider

Authentication存放认证信息，它需要被AuthenticationManager来认证，而AuthenticationManager管理着一堆的AuthenticationProvider
实际真正干活的就是AuthenticationProvider；ProviderManager是AuthenticationManager的主要实现。

## 四、第四节内容

* UserDetailsService
* DaoAuthenticationProvider
* AbstractUserDetailsAuthenticationProvider
* UsernamePasswordAuthenticationFilter
* FormLoginConfigurer

用户名密码登录的真谛就是DaoAuthenticationProvider

## 五、第五节内容

* SecurityContext
* SecurityContextHolder
* SecurityContextHolderStrategy
* SecurityContextPersistenceFilter
* SecurityContextHolderFilter
* SecurityContextRepository
* SecurityContextConfigurer

SecurityContext是贯穿我们使用SpringSecurity始末的一个重要级别的类，我们获取认证信息，都是需要通过它来获取。

## 六、第六节内容

* RememberMeAuthenticationFilter
* RememberMeAuthenticationProvider
* RememberMeAuthenticationToken
* RememberMeConfigurer
* RememberMeServices
* SessionManagementConfigurer
* SessionManagementFilter
* SessionAuthenticationStrategy
* SessionInformation
* SessionRegistry
* SessionInformationExpiredStrategy

RememberMe登录SpringSecurity也为我们考虑到了，同时也提供了现成的实现，可以说是拿来即用。
Session的相关管理SpringSecurity自然也不会落下，毕竟曾几何时，Session式的用户权限管理方案那是中小型企业入门最快、上手最容易的
方案

## 七、第七节内容

本节内容将主要讲解SpringSecurity剩余的除FilterSecurityInterceptor外过滤器，包括但不限于：

* AnonymousAuthenticationFilter
* ExceptionTranslationFilter
* LogoutFilter

这一整条链到底如何装配起来了，HttpSecurity到底做了什么？SpringSecurity顶层流程：
DelegatingFilterProxy -> FilterChainProxy -> SecurityFilterChain -> 具体的Filter
HttpSecurityConfiguration 配置了基础的 HttpSecurity 对象以供我们注入使用
WebSecurityConfiguration 注入了我们自己的 SecurityFilterChain Bean然后添加到 WebSecurity中
最终由 WebSecurity 构建出 FilterChainProxy 来执行SpringSecurity 的过滤逻辑

## 八、第八节内容

本节内容将进行实战教学，偏使用，主要讲解如果利用SpringSecurity快速实现我们的安全系统。
并拓展一种登录方式：短信登录

## 九、第九节内容

本节内容将先学习 AuthorizationFilter 这个用于授权的过滤器，与其相对应的还有 FilterSecurityInterceptor。
主要讲解如下类：

* AuthorizationFilter
* AuthorizationManager
* AuthorizationDecision
* AuthorizeHttpRequestsConfigurer
* RequestAuthorizationContext
* AuthorizationManagerRequestMatcherRegistry
* AbstractRequestMatcherRegistry
* AuthorizedUrl

## 十、第十节内容

本节内容将攻克SpringSecurity中的终极怪兽，授权处理过滤器FilterSecurityInterceptor。

* FilterSecurityInterceptor
* AbstractSecurityInterceptor
* FilterInvocation
* ConfigAttribute
* SecurityConfig
* WebExpressionConfigAttribute
* SecurityExpressionHandler
* AbstractInterceptUrlConfigurer
* UrlAuthorizationConfigurer
* ExpressionUrlAuthorizationConfigurer
* AbstractInterceptUrlRegistry
* AccessDecisionManager
* AccessDecisionVoter
* AffirmativeBased

## 十一、第十一节内容（实战）

本节内容将讲解SpringSecurity的实战，实现基Token于的访问模式

* 基于Redis的有状态的Token
* 基于JWT的无状态Token

## 十二、第十二节内容（最后一节）

本节内容将讲解SpringSecurity最后一块内容，基于方法级别的权限控制及其实现原理

* MethodSecurityInterceptor
* MethodInterceptor
* PrePostAnnotationSecurityMetadataSource