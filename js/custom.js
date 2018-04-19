$(document).ready(function(){
	//preloader
	$(window).load(function() { // makes sure the whole site is loaded
		  $('#status').fadeOut(); // will first fade out the loading animation
		  $('#preloader').delay(350).fadeOut('slow'); // will fade out the white DIV that covers the website.
		  $('body').delay(350).css({'overflow':'visible'});
		})
	//smoothScroll
	  smoothScroll.init();
	  //Scroll Spy		
	  $('body').scrollspy({ target: '.navbar' })
	//0wl-caurosel
	  $(".owl-carousel").owlCarousel(
	  {
		loop:true,
		margin:5,
		center:true,
		nav:false,
		dot:true,
		lazyload:true,
		slideSpeed:5000,
		paginationSpeed:5000,
		rewindSpeed:5000,	
		navigation :true,
		pagination:true,
		autoplay:true,
		autoplaySpeed:2000,
		responsive:{
			0:{
				items:1
			},
			600:{
				items:3
			},
			1000:{
				items:5
			}
		}
	}	  
	  );   
	//video popup
    $('.all-video-btn a').magnificPopup({
        type: 'iframe',
        iframe: {
            markup: '<div class="mfp-iframe-scaler">' +
                '<div class="mfp-close"></div>' +
                '<iframe class="mfp-iframe" frameborder="0" allowfullscreen></iframe>' +
                '</div>',
            patterns: {
                youtube: {
                    index: 'youtube.com/',
                    id: 'v=',
                    src: 'http://www.youtube.com/embed/%id%?autoplay=1'
                }
            },
            srcAction: 'iframe_src'
        }
    });
	//slick slider
	$('.item').slick({
	  dots: true,
	  speed:2000,
	  slidesToShow:1,
	  autoplay: true,
	  autoplaySpeed:2500,
	  adaptiveHeight: false

	});  
});