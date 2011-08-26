
	// timed updater
	function countdownUpdate() {

	    //shave 1 second off seconds_remaining and section_seconds_remaining
	    seconds_remaining -= 1; section_seconds_remaining -= 1;
	    var display_test_timer      = dateDiff(seconds_remaining);               //get timer display
	    var display_section_timer   = dateDiff(section_seconds_remaining);    //get section_timer display

	    //test timer - if we hit zero, autosubmit
	    if (seconds_remaining < 1 && show_timer != "N") {
	        //we've reached the end, autosubmit
	        ajaxSACWork(); clearInterval(countObject);
	        document.form1.TimerAutoSubmit.value = 'test';
	        if (typeof (isflashdone) != "undefined") { isflashdone == "true"; }
	        try {	//try to save audio
	            frm1ButtonClick = 'cmdEndTestTop';
	            do602Processing('cmdEndTestTop');
	            SubmitTheForm('cmdEndTestTop');
	        } catch (e) {
	            //if there is no 'do602processing', submit normally.
	            document.form1.submit();
	        } finally {
	            //document.form1.submit();
	        }
	    } else {
	        //update test timer.
	        if ($("tTimer")) { $("tTimer").innerHTML = display_test_timer }
	    }

	    //section timer - if we hit zero, autosubmit
	    if (section_seconds_remaining < 1 && show_section_timer != "N") {
	        ajaxSACWork(); clearInterval(countObject);
	        document.form1.TimerAutoSubmit.value = 'section';
	        if (typeof (isflashdone) != "undefined") { isflashdone == "true"; }
	        try {	//try to save audio
	            frm1ButtonClick = 'cmdEndTestTop';
	            do602Processing('cmdEndTestTop');
	            SubmitTheForm('cmdEndTestTop');
	        } catch (e) {
	            //if there is no 'do602processing', submit normally.
	            document.form1.submit();
	        } finally {
	            //document.form1.submit();
	        }
	    } else {
	        //update section timer
	        if ($("sTimer")) { $("sTimer").innerHTML = display_section_timer }
	    }

	    //section and test five minute warnings.
	    if (seconds_remaining == 300 && show_timer == "B") { alert($("testTimerWarning").innerHTML); }
	    if (section_seconds_remaining == 300 && show_section_timer == "B") { alert($("sectionTimerWarning").innerHTML); }
	}

    //date difference calculator
	function dateDiff(calced_seconds) {
	    timediff = calced_seconds * 1000;       //convert for milliseconds.

	    //break it down.
	    weeks = Math.floor(timediff / (1000 * 60 * 60 * 24 * 7));
	    timediff -= weeks * (1000 * 60 * 60 * 24 * 7);

	    days = Math.floor(timediff / (1000 * 60 * 60 * 24));
	    timediff -= days * (1000 * 60 * 60 * 24);

	    hours = Math.floor(timediff / (1000 * 60 * 60));
	    timediff -= hours * (1000 * 60 * 60);

	    mins = Math.floor(timediff / (1000 * 60));
	    timediff -= mins * (1000 * 60);

	    secs = Math.floor(timediff / 1000);
	    timediff -= secs * 1000;

	    //lets get rid of weeks, make it that many days.
	    days = days + (weeks * 7);

	    //buffer their digits.
	    if (secs < 10) { secs = "0" + secs; }
	    if (mins < 10) { mins = "0" + mins; }
	    if (hours < 10) { hours = "0" + hours; }


	    //RETURN RESULTS
	    return (days == 0)
        ? hours + ":" + mins + ":" + secs
        : days + "d " + hours + ":" + mins + ":" + secs;

	}

// initializer
function countdownInit() { countObject = setInterval('countdownUpdate()', 1000); }
//attach window onload to initializer
Event.observe(window,'load',countdownInit);		//on window load, lets do stuff.

//if no timers, remove DIVs
if (show_timer=="N" || show_timer==""){if($("testTimer")){$("testTimer").hide();}}
if (show_section_timer=="N"|| show_section_timer==""){if($("sectionTimer")){$("sectionTimer").hide();}}
