Android Charitree (alpha)
=========================

A android mobile platform for the public to virtualize the process of donating new or
unwanted items to charity organizations. Personnel from non-government organisations
(NGO), charities and Institutions of A Public Character (IPC) will be able to use this mobile
platform to publish donation drive/campaigns to the public, accept donations and assign
volunteer to collect the donations.

Android Charitree is currently released as an alpha and is under heavy development. To view the
latest changes, please visit
[here](https://github.com/Harrizontal/Charitree/commits/master).
Note that some changes (such as database schema modifications) are not backwards
compatible during this alpha period and may cause the app to crash. In this
case, please uninstall and re-install the app.

Getting Started
---------------
Download and extract the file

Under Android studio, select File and Open the extracted file

Let gradle build and you are able to run

Scope of the mobile application
---------------

The scope of the system for “Charities” are:
1. Create two distinct user modes - Donor and Campaign Manager
2. An user interface and web services to register, login and logout for both users
3. An user interface and web services for Donor to
	* Show a list of campaigns
	* Show a list of donations donated by user
	* Show a specific donation by user
	* Create a donation request to a specific campaign
	* Show a virtual tree that grows in following of the number of donations donated by the Donor.
	* Register as a Campaign Manager
4. An user interface and web services for Campaign Manager to
	* Create campaign for Campaign Manager
	* View a campaign information and its forecasted weather created by the Campaign Manager
	* Edit campaign information for Campaign Manager
	* Manage donations of a specific campaign and assignment of volunteer for Campaign Manager
