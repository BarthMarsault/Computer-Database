$(function() {
  $("#addComputerForm").validate({
    rules: {
      computerNameValue: {
		required : true,
		minlength : 2,
	  },
	  introducedValue: {
		date : true, 
	  },
	  discontinuedValue: {
		date: true,
		after: "#introduced"
	  },
    },
    messages: {
      computerNameValue: "Name is required and minimum length of 2",
	  introducedValue: "Please enter a valid date",
	  discontinuedValue : {
		date: "Please enter a valid date",
		after: "Discontinued date can't without or before introduced date"
		}
    },
    submitHandler: function(form) {
      form.submit();
    }
  });
});


jQuery.validator.addMethod("after", 
function(disc, element, intr) {
    if (!/Invalid|NaN/.test(new Date(disc)) && !/Invalid|NaN/.test(new Date($(intr).val()))){
		return new Date(disc) > new Date($(intr).val());
    }
	else if (!/Invalid|NaN/.test(new Date(disc)) && /Invalid|NaN/.test(new Date($(intr).val()))){
		return false;
	}
    return true; 
});
