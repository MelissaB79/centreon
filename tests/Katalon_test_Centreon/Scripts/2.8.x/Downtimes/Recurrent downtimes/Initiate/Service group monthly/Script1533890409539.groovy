import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import groovy.time.TimeCategory

def config = TestDataFactory.findTestData('Configuration')

def today

use(TimeCategory, {
	today = new Date()
})

WebUI.openBrowser(config.getValue('url', 1))

//********************************************************Login as an admin********************************************************//

WebUI.setText(findTestObject('General/Login/input_useralias'), config.getValue('login', 1))

WebUI.setText(findTestObject('General/Login/input_password'), config.getValue('password', 1))

WebUI.click(findTestObject('General/Login/input_submitLogin'))

//****************************************************Go to Recurrent downtimes****************************************************//

CustomKeywords.'custom.NavigationMonitoring.accessRecurrentDowntimes'()

WebUI.waitForPageLoad(3)

//********************************************************Create a downtime********************************************************//

WebUI.click(findTestObject('Monitoring/Recurrent downtimes/a_Add'))

WebUI.delay(1)

WebUI.setText(findTestObject('Monitoring/Recurrent downtimes/input_downtime_name'),
	config.getValue('TimeIndicator', 1) + 'Katalon_service_group_monthly')

//Select the monthly basis period
def element = WebUI.modifyObjectProperty(findTestObject('Monitoring/Recurrent downtimes/radio_period'),
	'value', 'equals', 'monthly_basis', true)

WebUI.click(element)

WebUI.delay(1)

//These following lines click on the current day
//This array contains the current day splited, the name of the day is in the first case
def array = today.toString().split(' ')

element = WebUI.modifyObjectProperty(findTestObject('Object Repository/Monitoring/Recurrent downtimes/input_Monthly day'),
	'value', 'equals', today.getDate().toString(), true)

WebUI.click(element)

//****************************************************Configure the time period****************************************************//

//Fill the Time Period form - start time
WebUI.click(findTestObject('Monitoring/Recurrent downtimes/input_tp start'))

WebUI.delay(1)

WebUI.click(findTestObject('Monitoring/Recurrent downtimes/input_tp start'))

def hours
def minutes
	
if(today.getMinutes() > 49){
	if(today.getMinutes() > 54) minutes = 10
	else minutes = 5
	if (today.getHours() == 23){
		hours = 0
	} else{
		hours = today.getHours() + 1
	}
} else {
	minutes = today.getMinutes() - (today.getMinutes() % 5) + 10
	hours = today.getHours()
}

element = WebUI.modifyObjectProperty(findTestObject('Monitoring/Recurrent downtimes/td_Hours'),
	'data-hour', 'equals', hours.toString(), true)

WebUI.click(element)

element = WebUI.modifyObjectProperty(findTestObject('Monitoring/Recurrent downtimes/td_Minutes'),
	'data-minute', 'equals', minutes.toString(), true)

WebUI.click(element)

//Fill the Time Period form - end time
WebUI.click(findTestObject('Monitoring/Recurrent downtimes/input_tp end'))

WebUI.delay(1)

if(hours != 23){
	hours = hours + 1
} else{ hours = 0 }

element = WebUI.modifyObjectProperty(findTestObject('Monitoring/Recurrent downtimes/td_Hours'),
	'data-hour', 'equals', hours.toString(), true)

WebUI.click(element)

element = WebUI.modifyObjectProperty(findTestObject('Monitoring/Recurrent downtimes/td_Minutes'),
	'data-minute', 'equals', minutes.toString(), true)

WebUI.click(element)

//*******************************************************Link a service group******************************************************//

WebUI.click(findTestObject('Monitoring/Recurrent downtimes/a_Relations'))

element = WebUI.modifyObjectProperty(findTestObject('Monitoring/Recurrent downtimes/input_Hosts'),
	'placeholder', 'equals', 'Linked with Service Groups', true)

WebUI.setText(element, config.getValue('TimeIndicator', 1) + 'monthly_sg')

element = WebUI.modifyObjectProperty(findTestObject('Monitoring/Recurrent downtimes/div'),
	'title', 'equals', config.getValue('TimeIndicator', 1) + 'monthly_sg', true)

WebUI.click(element)

WebUI.click(findTestObject('General/input_submitA'))

WebUI.click(findTestObject('Old menu/a_Logout'))

WebUI.closeBrowser()