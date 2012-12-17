/**
 * JavaScript code to accompany main.php for sorter service using jQuery
 */

/**
 * Got this code from http://jquery-howto.blogspot.com/2009/09/get-url-parameters-values-with-jquery.html 
 * on 10/10/2012 at 11:05AM EST USA UTC-05:00
 */

function getUrlVars()
{
    var vars = [], hash;

    //var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');

    //var prehashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('#');
    //var hashes = prehashes[0].split('&');

    // too much
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('#')[0].split('&');

    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}

/**
 * Call when jQuery knows that the document is loaded
 */
function document_ready_jquery() {
    //alert("Begin script document_ready_jquery");
    $("#test_op").click(function(e)
    { 
        e.preventDefault(); 
        list_items_test_op(); 
    }); 

   /* $("list_items_form").submit(function(event))
    {
        //event.preventDefault();

        var $form = $(this);

        alert("Submitting form: " + $form);
    });*/



    $("#sort_btn").click(function(event)
    { 
        // Prevent default form submission
        event.preventDefault(); 

        // Get form data
        var formData = $("#input_items_form").serialize();

        // Process AJAX request
        $.ajax(
        {
            type: "post",
            url: "data_xml",
            dataType : "xml",
            data: formData,
            success: function(data)
            {
                    // Clear div
                    $("#sorted_list").empty();

                    // Unsorted Items
                    $("#sorted_list").append("<h4>Unsorted Items: </h4>");

                    var ul = $('<ul>').appendTo('#sorted_list');

                    $(data).find("unsorted_item").each(function()
                    {
                            var thisItem = $(this).text();

                            ul.append(
                              $(document.createElement('li')).text(thisItem)
                            );
                    });


                    // Sorted Items
                    $("#sorted_list").append("<h4>Sorted Items: </h4>");

                    var ul = $('<ul>').appendTo('#sorted_list');

                    $(data).find("sorted_item").each(function()
                    {
                            var thisItem = $(this).text();

                            ul.append(
                              $(document.createElement('li')).text(thisItem)
                            );
                    });

            }
        });
    }); 

   

    //alert("End script document_ready_jquery");


    
    /*$('#input_items_items_form').bind('submit', function (e) {
        e.preventDefault();
        $.post('main.php#list_items', $(this).serialize(), function (response) {
            $.mobile.changePage('#list_items', 
                {transition: 'fade',
                data: $(this).serialize()});
            alert("something happened");
        });
    });
  */
}

/**
 * Input items page loaded
 */
function input_items_init() {
    ni = getUrlVars()["num_items"];

    //alert("Begin scripthhhhh, num_items=" + ni);
    //ni = 5;
    //ni = $('#num_items').val();
    iic = $('.input_items_collection');
    for(i = 0; i < ni; i++)
    {
        item_num = i + 1;
        iic.append(
            '<p>' +
            '<label for="items' + item_num + '">Item ' + item_num + '</label>' +
            '<input type="text" name="item' + item_num + '" id="item' + item_num + '"/>' +
            '</p>'
        );
    }
    iic.trigger('create');
    
    //alert("End script");
}

/**
 * Load contents of list_items_collection element asynchronously in response to some action
 */



function list_items_init()
{
    alert("Triggered list items init function");
    $(".input_items_collection").show();
}

function list_items_test_op()
{ 
    //alert("Begin script list_items_test_op");
    $(".list_items_collection").show(); 
    var xval1 = $("#xval1").val(); 
    var xval2 = $("#xval2").val();

    var num_items_x = 2;
    var item_key_base_name = "val";
    var item_data_base_name = "xval";
    var items = new Object();
    for(i = 0; i < num_items_x; i++)
    {
        var key_name = item_key_base_name + (i+1);
        var data_element_name = "#" + item_data_base_name + (i+1);
        items[key_name] = $(data_element_name).val();
    }

    /* 
    $.post("data.php", {val1 : xval1, val2: xval2}, function(data)
    {
        if (data.length>0)
        { 
            $("#list_items_op_result").html(data); 
        }
        else
        {
            alert("Unable to process request for addition operation");
        }
    });
    */

    $.ajax(
    {
        type: "post",
        url: "data.php",
        dataType : "html",
        data: {val1 : xval1, val2: xval2},
        success: function(data)
        {
            if (data.length>0)
            { 
                // TEMP DISABLED //$("#list_items_op_result").html(data); 
            }
            else
            {
                alert("Unable to process request for addition operation");
            }
        }
    });

    $.ajax(
    {
        type: "post",
        url: "data-xml.php",
        dataType : "xml",
        data: {val1 : xval1, val2: xval2},
        success: function(data, textStatus)
        {

            //alert("list_items_test_op data: " + data + " length: " + data.length);
            //if (data.length>0)
            if(textStatus == "success")
            { 
                //$("#list_items_op_result").html(data);
                $(data).find("add").each(function()
                {
                    var sum = $(this).text();
                    sum = sum * 2;
                    //$("#list_items_op_result").append($(this));
                    //$("#list_items_op_result").html($(this).text());
                    //$("#list_items_op_result").html($(this).attr("level"));
                    // TEMP DISABLED // $("#list_items_op_result").html("<p>" + sum + "</p>");
                    //$("#list_items_op_result").append("<p>" + sum + "</p>");
                }); 
            }
            else
            {
                alert("Unable to process request for addition operation (in XML). Text Status: " + textStatus);
            }
        }
    });
    
    $.ajax(
    {
        type: "post",
        url: "data-json.php",
        //type: "get",
        //url: "data.json",
        dataType: "json",
        //data: {"val1" : xval1, "val2": xval2},
        data: items,
        success: function(data, textStatus)
        {

            //alert("list_items_test_op data: " + data + " length: " + data.length);
            //if (data.length>0)
            if(textStatus == "success")
            { 
                //$("#list_items_op_result").html(data);
                //alert("JSON data: " + data.math_op.results.add);
                var sum = data.math_op.results.add;
                sum = sum * 2;
                //$(data).find("add").each(function()
                $("#list_items_op_result").html("<p>Sum is " + sum + "</p>");
                $.each(data.math_op.results, function(key, val) {
                    //$("#list_items_op_result").append($.html(
                    //    "<p>" + key + " " + val + "</p>"));
                    alert("key:val " + key + ":" + val);
                });
                //{
                //    var sum = $(this).text();
                //    sum = sum * 2;
                    //$("#list_items_op_result").append($(this));
                    //$("#list_items_op_result").html($(this).text());
                    //$("#list_items_op_result").html($(this).attr("level"));
                    // TEMP DISABLED // $("#list_items_op_result").html("<p>" + sum + "</p>");
                    //$("#list_items_op_result").append("<p>" + sum + "</p>");
                //}); 
            }
            else
            {
                alert("Unable to process request for addition operation (in JSON). Text Status: " + textStatus);
            }
        }
    });

    //alert("End script list_items_test_op");
}
