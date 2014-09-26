DipeshCalenderLibrary
=====================

<h2>Purpose</h2>

<p>This Calender Library is the easy way to integrate calender on your android application. The library opens Calender activity inside the app once the library is called. Programmers can also add the event on any date which will be saved to app's sqllite database. Now, when user clicks on that date of calender gridview, the event and description will be shown. Also, event days are marked red in the calender and current day is marked blue. The calender can also be periodically updated by deleting all previous events and adding new events programatically. This library also provides the function to notify user a day before the event date by poping notification using AlarmManager. However programmers can choose whether to show notifications or not for a particular event.</p>

<h2>Setup</h2>

<p>Check out the project and add DipeshCalenderLibrary to your project as an Android Library Project. </p>
<p>Open your project.properties file and add the following line at the last. manifestmerger.enabled=true. </p>

<h2>Examples</h2>

<h3>To Open DipeshCalender Activity:</h3>

<p>DipeshCalender mCalender = new DipeshCalender(this, CurrentActivity.this);</p>
<p>mCalender.startCalenderActivity("My Calender");</p>

<h3>To Add event to calender:</h3>

<p> mCalender.addEvent(2014, 8, 29, "This is Title", "This is description", true);</p>

<p> //first three paramaters are year,month and day of event respectively. fourt and fifth parameters are title of event and description which will be shown</p>
<p> //when particular day is clicked. last parameter is a boolean for whether or not to notify user a day ago of the event. If set true // notification is shown.</p>

<h3>To Delete all events:</h3>

<p> mCalender.deleteAppEvents();</p>
<p> //Deletes all events from apps local database. this can be done every time before updating the calender events.</p>

<h3>Customization:</h3>

<p> The actionbar title of calender activity can be customised by passing mCalender.startCalenderActivity("custom actionbar title"); to activity</p>
<p> notification icon and title can also be customized by calling mCalender.setNotification("custom notification title",resource_id);</p>
