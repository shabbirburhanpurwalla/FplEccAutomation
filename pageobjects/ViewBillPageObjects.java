package pageobjects;

import static pageobjects.ObjectLocator.*;

/**
 * Class to store all the objects on View Bill Page
 * 
 * @author 387478
 * @lastmodified 15 October
 *
 */
public enum ViewBillPageObjects {
	
	lnk_ViewBill("View Bill",LINKTEXT,"ViewBill Link"), //View Bill Link
	lbl_ViewBillError("errorMessage_div",ID,"ViewBill Page"), //View Bill Error
	lnk_SelectAnotherAccount("Select Another Account",LINKTEXT,"SelectAnotherAccount Link"), //Select Another Account Link
	
	//RightSection
	//Keep In Mind
	lbl_KeepInMind("//*[@id='my-slider']/span/h3",XPATH,"Keep In Mind label"),
	lbl_KeepInMindMsg("//*[@id='my-slider']/div/div[2]/div[2]/div",XPATH,"Keep In Mind Message"),
	lbl_KeepInMindExpandedMsg("body > div.tooltip",CSS,"Keep In Mind Expanded Message"),
	lbl_KeepInMindMsg1("messageKIM_GS_",ID,"Keep In Mind Message"),
	lnk_KeepInMindMore("//*[@id='messageKIM_GS_",XPATH,"Kind in Mind Message --> More Link"),//msgTip
	lnk_KepInMindNext("#my-slider > div > div.slide-wrapper > div > a.next",CSS,"Keep In Mind Next Link"),
	
	lnk_EmailUpdate("email update",LINKTEXT,"Email Update Link"),
	//lnk_EmailUpdate(".//*[@id='emailUsDiv']/fieldset/ul/li/p[8]/a",XPATH,"Email Update Link"),
	lnk_EnergyNews("Sign up for energy news",LINKTEXT,"EnergyNews Link"),
	lnk_ContactUs("Contact us",LINKTEXT,"ContactUs Link"),
	
	lbl_EmailShort("emailLong",ID, "Email Address"),
	lbl_EmailLong("fullEmail",ID, "Email Address Full"),
	
	
	
	//Pay Bill Button
	lnk_PayBillblueBand("bannerCenterTable_PAY_BILL_1_IMG",ID,"'Pay Bill' balance banner button"),
	lnk_PayBillblueBand1("Pay Bill",LINKTEXT,"'Pay Bill' balance banner button"),
	lnk_PayBillPaymentMessageTable("paymentMessageTable_PAY_BILL_2_IMG",ID,"'Pay Bill' Payment Message Table"),
	lnk_PayBill("paymentMessageTable_PAY_BILL_2_IMG",ID,"'Pay Bill' button"),
	
	
	//My Bill Section
	lbl_customerName("custInfoNestedInnerLeftTable_CSFR_NM_CUST_1",ID,"Customer Name"),
	lbl_ABPMessage("paymentMessageTable_LITR_BILL_DESCR_4_DO_NOT_PAY",ID,"ABP Message"),
	lbl_serviceAddress("custInfoNestedInnerLeftTable_CSFR_AD_SERV",ID,"Service Address"),
	lbl_LastBillDate("custInfoLeftTable_CSFR_DT_BILL",ID,"Last Bill Date"),//Last Bill date
	lnk_SelectAnotherAccountMyBill("Select Another Account",LINKTEXT,"'Select Another Account' Link"),//Select Another Account Link
	lnl_ViewAccountSummary("View Account Summary",LINKTEXT,"'View Account Summary' Link"), //View Account Summary Link
	lnk_ViewBillInsert("View Bill Insert",LINKTEXT,"'View Bill Insert' Link"),//View Bill Insert Link
	lnk_DownloadBill("custInfoNestedInnerLeftTable_E_BILL_IMG",ID,"DownloadBill Link"), //Download Bill link
	drpdwn_ServiceDate("#custInfoNestedInnerLeftTable_SVC_DATE_SELECT > #billdateSelect",CSS,"Service Date Dropdown"),
	lbl_CreditAmount("paymentMessageTable_LITR_BILL_DESCR_4_DO_NOT_PAY",ID,"Credit Amount Message"),
	//paymentMessageTable_LITR_BILL_DESCR_4_DO_NOT_PAY
	
	lbl_StatementBalance("newChargesTable_LITR_TOTAL_AMOUNT_YOU_OWE",ID,"Statement Balance Line"),// Statement Balance Line
	lbl_StatementBalanceAmount("newChargesTable_CSFR_AT_TOT_NET",ID,"Statement Balance Amount"),// Statement Balance Amount
	lbl_previousBalance("previousBalanceTable_PrevBalance",ID,"Previous balance header"),//Previous Balance Header
	lbl_lastBillAmount("previousBalanceTable_CSFR_AT_TOT_NET_PREV",ID,"Previous Bill Amount"),//Last Bill Amount
	//previousBalanceTable_PrevBalance
	
	//New Charges Section
	lbl_NewChargesHeader("newChargesHeader_NewCharges",ID,"New Charges Header"),//New charges header
	lbl_NewChargesAmount("newChargesHeader_CSFR_AT_TOT_CB",ID,"New Charges Amount"),//New charges amount
	lbl_Rate("newChargesTable_LITR_RATE",ID,"Rate"),//Rate
	lbl_TotalNewCharges("newChargesTable_LITR_TOTAL_NEW_CHARGES",ID,"Total New Charges header"),//Total New Charges
	lbl_TotalNewChargesAmount("newChargesTable_CSFR_AT_TOT_ELE_CHRGES",ID,"Total New Charges Amount"),//Total New Amount
	lbl_LPC("newChargesTable_LITR_LATE_PAYMENT_CHARGE",ID,"Late Payment Charge header"),//LPC header
	lbl_LPCAmount("newChargesTable_CSFR_AT_LPC_DB",ID,"Late Payment Charge Amount"),//LPC Amount
	lbl_ElectricityHeader("newChargesTable_Electricity",ID,"Electricity Header"), //Electricity Header
	lbl_ElectricityCharges("newChargesTable_CSFR_AT_ELE_CHG",ID,"Electricity Charges"), //Electricity Charges
	//
	lbl_CustomerChargeHeader("newChargesTable_VBDR_LABEL01",ID,"Customer Charge label"),
	lbl_CustomerCharge("newChargesTable_VBDR_VALUE01",ID,"Customer Charge"),//
	lbl_NonFuelHeader("newChargesTable_VBDR_LABEL02",ID,"Non Fuel Label"),
	lbl_NonFuelCharge("newChargesTable_VBDR_VALUE02",ID,"Non Fuel Charge"),
	lbl_FuelHeader("newChargesTable_VBDR_LABEL03",ID,"Fuel Label"),
	lbl_FuelCharge("newChargesTable_VBDR_VALUE03",ID,"Fuel Charge"),
	
	//newChargesTable_CSFR_AT_LPC_DB
	
	
	//newChargesHeader_CSFR_DT_DUE
	
	//Account History
	lnk_Yourbills("Your bills",LINKTEXT,"YourBills section"), // "Your Bills" link in Account History section
	lnk_Yourbillsproperty("//*[@id='tabs']/li[1]",XPATH,""),
	lnk_YourPayments("Your payments",LINKTEXT,"YourPayments section"), // "Your Payments" link in Account History section
	lnk_YourPaymentsProperty("//*[@id='payTab']",XPATH,"YourPayments section"),
	
	
	lnk_Yourbills_history("//*[@id='tabBills']/tbody/tr",XPATH,"Previous Bills in Your bills"),
	lnk_Yourpayments_history("//*[@id='tabPayments']/tbody/tr",XPATH,"Previous Bills in Your bills"),
	
	//Balancebanner
	lbl_SelectAccount(".//*[@id='outerMostTable']/tbody/tr[2]/td/table/tbody/tr[2]/td[2]/table/tbody/tr/td[1]/form/table[1]/tbody/tr/td/table/tbody/tr[1]/td/h5",XPATH,"Select Account Label"),
	lbl_CustomerName("bannerCenterTable_CSFR_NM_CUST_1",ID,"CustomerName label"), //Customer Name
	lbl_BalanceLine("bannerCenterTable_BALANCE_LINE",ID,"Balance Line"), //balance Line
	
	
	
	//Comparisons Section
	lbl_MeterComparison("meterComparisonLeftTable_COMPARISONS",ID,"MeterComparison Label"), //Meter Comparison header
	lbl_meterNumber("meterComparisonLeftTable_CSFR_CD_MTR_NO",ID,"Meter Number label"),//MeterNo
	lbl_NextReadDate("meterComparisonLeftTable_CSFR_DT_RDGTO_R_DT_NEXT_READ",ID,"Next Meter Read Date label"),//NextReadDate
	lbl_ReadingDateToCurrentMonth("meterComparisonCenterTable_BH_DATE_READING_TO",ID,"Current Read Date"), // Current Month Bill Date
	lbl_ReadingDateToLastMonth("meterComparisonCenterTable_DT_RDG_TOO_BH",ID,"Last Read Date"), // Last month bil date
	lbl_ReadingDateToCurrentMonthDays("meterComparisonCenterTable_CSFR_QY_DA_USE_MD",ID,"Days in current bill"), //Number of days in current bill
	lbl_ReadingDateToLastMonthDays("meterComparisonCenterTable_QY_DA_USEE_BH",ID,"Days in Last bill"), //Number of days in last bill
	lbl_CurrentMonthCons("meterComparisonCenterTable_CSFR_QY_CONS_UNIT_TM",ID,"Current month kWh consumption"),
	lbl_LastMonthCons("meterComparisonCenterTable_QY_CONS_UNIT",ID,"Last month kWh consumption"),
	//meterComparisonCenterTable_CSFR_QY_CONS_UNIT_TM
	lbl_CurrentMonthAvgUsage("meterComparisonCenterTable_CSFR_QY_AVG_CONS_MD",ID,"Current Bill Average Usage"),
	lbl_LastMonthAvgUsage("meterComparisonCenterTable_QY_KWH_DA_AVGG_BH",ID,"Last Bill Average Usage"),
	lbl_CurrentMonthCharges("meterComparisonCenterTable_SYSR_COMP_AT_TOT_ELE_CHG_MD",ID,"Current Bill Charges"),
	lbl_LastMonthCharges("meterComparisonCenterTable_ATT_TOT_CONS",ID,"Last Bill Charges"),
	lbl_MonthComparisonHeader("meterComparisonCenterTable_MON_COMP_1",ID,"Month Comparison Label"),
	

	
	//AMI
	lbl_EnergyUseSectionHeader("energyUseSectionHeader",ID,"EnergyUsageSection Label"), //EnergyUse Section
	img_AmiLegend("amiLegendImg",ID,"AMI Legend"),//AMI Legend
	lbl_AmiMessage("#graphHeaderDiv > #message--msg-ami > p.dollar",CSS,"AMI Message"),//AMI Message
	img_NonAmiLegend("nonAmiLegendImg",ID,"Non AMI Legend"),//AMI Legend
	lbl_NonAmiMessage("#graphHeaderDiv > #message--msg-non-ami > p.dollar",CSS,"Non AMI Message"),//AMI Message
	lnl_onlineEnergyDashboard("accessing your online energy dashboard.",LINKTEXT,"Online Energy Dashboard Link"),
	obj_AmiGraph("dashboard",ID,"AMI GRAPH"),//AMI Graph
	obj_NonAmiGraph("nonAMIGraph1",ID,"Non AMI GRAPH"), //Non - AMI Graph
	
	//Greetings Line
	
	lnk_GreetingLine("bannerCenterTable_GREETINGS_LINE",ID,"Greetings Line"), //Greetings Line
	
	//ToolTip Messages	
	lbl_BalanceLineTooltip("bannerCenterTable_BALANCE_LINEHelpMsg",ID,"Balance Line Tooltip"), //Balance Line tooltip image
	lbl_BalanceLineTooltipBub("bannerCenterTable_BALANCE_LINEHelpMsgBub",ID,"Balance Line Tooltip"), //Balance Line tooltip message
	lbl_BalanceLineTooltipClose("//*[@id='bannerCenterTable_BALANCE_LINEHelpMsgBub']/div/a",XPATH,"Close Balance Line Tooltip"),//Close Balance Line tooltip message
	
	lbl_KeepInMindTooltip(".//*[@id='messageKIM_GS_1']/a",XPATH,"Keep In Mind Tooltip"), //Keep in Mind tooltip image
	lbl_KeepInMindTooltipBub("/html/body/div[17]",XPATH,"Keep In Mind Tooltip"), //Keep in Mind Line tooltip message	
	
	lbl_NewChargesTable_LITR_RATEHelpMsg9("newChargesTable_LITR_RATEHelpMsg",ID,"RateHelpMsg Tooltip"), //Rate tooltip Image
	lbl_NnewChargesTable_LITR_RATEHelpMsgBub("newChargesTable_LITR_RATEHelpMsgBub",ID,"RateHelpMsg Tooltip"), //Rate tooltip message
	lbl_NnewChargesTable_LITR_RATEHelpMsgClose("//*[@id='newChargesTable_LITR_RATEHelpMsgBub']/div/a",XPATH,"Close RateHelpMsg Tooltip"),//
	
	lbl_NewChargesTable_VBDR_CustomerChargeHelpMsg("newChargesTable_VBDR_LABEL01HelpMsg",ID,"Customer Charge Tooltip"),//Customer Charge tooltip image
	lbl_NewChargesTable_VBDR_CustomerChargeHelpMsgBub("newChargesTable_VBDR_LABEL01HelpMsgBub",ID,"Customer Charge Tooltip"), //Customer Charge tooltip message
	lbl_NewChargesTable_VBDR_CustomerChargeHelpMsgClose("//*[@id='newChargesTable_VBDR_LABEL01HelpMsgBub']/div/a",XPATH,"Close Customer Charge Tooltip"),
	//
	
	lbl_NewChargesTable_VBDR_NonFuelHelpMsg("newChargesTable_VBDR_LABEL02HelpMsg",ID,"NonFuel Tooltip"), //NonFuel tooltip image
	lbl_NewChargesTable_VBDR_NonFuelHelpMsgBub("newChargesTable_VBDR_LABEL02HelpMsgBub",ID,"NonFuel Tooltip"), //NonFuel Tooltip message
	lbl_NewChargesTable_VBDR_NonFuelHelpMsgClose("//*[@id='newChargesTable_VBDR_LABEL02HelpMsgBub']/div/a",XPATH,"Close NonFuel Tooltip"), 
	//
	
	lbl_NewChargesTable_VBDR_FuelHelpMsg("newChargesTable_VBDR_LABEL03HelpMsg",ID,"Fuel Tooltip"), //Fuel tooltip image
	lbl_NewChargesTable_VBDR_FuelHelpMsgBub("newChargesTable_VBDR_LABEL03HelpMsgBub",ID,"Fuel Tooltip"),//Fuel tooltip message	
	lbl_NewChargesTable_VBDR_FuelHelpMsgClose("//*[@id='newChargesTable_VBDR_LABEL03HelpMsgBub']/div/a",XPATH,"Close Fuel Tooltip"), 
	
	lbl_NewChargesTable_LITR_ON_CALL_CREDITHelpMsg("newChargesTable_LITR_ON_CALL_CREDITHelpMsg",ID,"CallCredit Tooltip"),//Call Credit tooltip image
	lbl_NewChargesTable_LITR_ON_CALL_CREDITHelpMsgBub("newChargesTable_LITR_ON_CALL_CREDITHelpMsgBub",ID,"CallCredit Tooltip"),//Call Credit tooltip message
	
	 lbl_NewChargesTable_LITR_STORM_CHARGE_OTHERHelpMsg("newChargesTable_LITR_STORM_CHARGE_OTHERHelpMsg",ID,"Storm Charge Tooltip"), //Storm Charge tooltip image
	 lbl_NewChargesTable_LITR_STORM_CHARGE_OTHERHelpMsgBub("newChargesTable_LITR_STORM_CHARGE_OTHERHelpMsgBub",ID,"Storm Charge Tooltip"), //Storm Charge tooltip message
	
	 lbl_NewChargesTable_LITR_GROSS_RECEIPTS_TAX_INCRHelpMsg("newChargesTable_LITR_GROSS_RECEIPTS_TAX_INCRHelpMsg",ID,"Gross Recipt Tooltip"), //Gross Recipt tooltip image
	 lbl_NewChargesTable_LITR_GROSS_RECEIPTS_TAX_INCRHelpMsgBub("newChargesTable_LITR_GROSS_RECEIPTS_TAX_INCRHelpMsgBub",ID,"Gross Recipt Tooltip"), //Gross Recipt tooltip message
	
	 lbl_NewChargesTable_LITR_FRANCHISE_CHARGEHelpMsg("newChargesTable_LITR_FRANCHISE_CHARGEHelpMsg",ID,"Franchise Charge Tooltip"), //Franchise charge tooltip image
	 lbl_NewChargesTable_LITR_FRANCHISE_CHARGEHelpMsgBub("newChargesTable_LITR_FRANCHISE_CHARGEHelpMsgBub",ID,"Franchise Charge Tooltip"), //Franchise charge tooltip message
	
	 lbl_NewChargesTable_LITR_FLORIDA_SALES_TAXHelpMsg("newChargesTable_LITR_FLORIDA_SALES_TAXHelpMsg",ID,"Florida Sales Charge Tooltip"), //Florida sales charge tooltip image
	 lbl_NewChargesTable_LITR_FLORIDA_SALES_TAXHelpMsgBub("newChargesTable_LITR_FLORIDA_SALES_TAXHelpMsgBub",ID,"Florida Sales Charge Tooltip"), //Florida sales charge tooltip message
	
	 lbl_NewChargesTable_LITR_DISCRETIONARY_SALE_SURTAXHelpMsg("newChargesTable_LITR_DISCRETIONARY_SALE_SURTAXHelpMsg",ID,"Discretionary sales surtax Tooltip"), //Discretionary sales surtax tooltip image
	 lbl_NewChargesTable_LITR_DISCRETIONARY_SALE_SURTAXHelpMsgBub("newChargesTable_LITR_DISCRETIONARY_SALE_SURTAXHelpMsgBub",ID,"Discretionary sales surtax Tooltip"), //Discretionary sales surtax tooltip message
	
	 lbl_NewChargesTable_LITR_UTILITY_TAXHelpMsg("newChargesTable_LITR_UTILITY_TAXHelpMsg",ID,"Utility Tax Tooltip"), //Utility tax tooltip image
	 lbl_NewChargesTable_LITR_UTILITY_TAXHelpMsgBub("newChargesTable_LITR_UTILITY_TAXHelpMsgBub",ID,"Utility Tax Tooltip"), //Utility tax tooltip message
	 
	 lbl_StatementBalanceTooltip("newChargesTable_LITR_TOTAL_AMOUNT_YOU_OWEHelpMsg",ID,"Statement Balance Tooltip"), //Statement Balance tooltip image
	 lbl_StatementBalanceTooltipBub("newChargesTable_LITR_TOTAL_AMOUNT_YOU_OWEHelpMsgBub",ID,"Statement Balance Tooltip"), //Statement Balance tooltip message
	 
	 lbl_MeterComparisonTooltip("meterComparisonCenterTable_MON_COMP_1HelpMsg",ID,"Meter Comparison Tooltip"), //Meter Comparison tooltip image
	 lbl_MeterComparisonTooltipBub("meterComparisonCenterTable_MON_COMP_1HelpMsgBub",ID,"Meter Comparison Tooltip"), //Meter Comparison tooltip message
	 lbl_MeterComparisonMessage("meterComparisonCenterTable_MON_COMP_1",ID,"Meter Comparison Message"),
	 
	 lbl_MeterComparisonDayUseTooltip("meterComparisonCenterTable_CSFR_QY_DA_USE_MDHelpMsg",ID,"Meter Comparison Day Use Tooltip"), //Meter Comparison Day Use tooltip image
	 lbl_MeterComparisonDayUseTooltipBub("meterComparisonCenterTable_CSFR_QY_DA_USE_MDHelpMsgBub",ID,"Meter Comparison Day Use Tooltip"), //Meter Comparison Day Use tooltip message
	 lbl_MeterComparisonDayUseTooltipClose(".//*[@id='meterComparisonCenterTable_CSFR_QY_DA_USE_MDHelpMsgBub']/div/a",XPATH,"Close Meter Comparison Day Use Tooltip"), //Close Meter Comparison Day Use tooltip message
	 
	 lbl_MeterComparisonUnitConsTooltip("meterComparisonCenterTable_CSFR_QY_CONS_UNIT_TMHelpMsg",ID,"Meter Comparison UnitsCons Tooltip"), //Meter Comparison Units Comsumed tooltip image
	 lbl_MeterComparisonUnitConsTooltipBub("meterComparisonCenterTable_CSFR_QY_CONS_UNIT_TMHelpMsgBub",ID,"Meter Comparison UnitsCons Tooltip"), //Meter Comparison Units Comsumed tooltip message
	 lbl_MeterComparisonUnitConsTooltipClose(".//*[@id='meterComparisonCenterTable_CSFR_QY_CONS_UNIT_TMHelpMsgBub']/div/a",XPATH,"Meter Comparison UnitsCons Tooltip"), //Close Meter Comparison Units Comsumed tooltip message
	 
	 lbl_MeterComparisonAvgConsTooltip("meterComparisonCenterTable_CSFR_QY_AVG_CONS_MDHelpMsg",ID,"Meter Comparison AvgCons Tooltip"), //Meter Comparison Average Comsumption tooltip image
	 lbl_MeterComparisonAvgConsTooltipBub("meterComparisonCenterTable_CSFR_QY_AVG_CONS_MDHelpMsgBub",ID,"Meter Comparison AvgCons Tooltip"), //Meter Comparison Average Comsumption tooltip message
	 lbl_MeterComparisonAvgConsTooltipClose("//*[@id='meterComparisonCenterTable_CSFR_QY_AVG_CONS_MDHelpMsgBub']/div/a",XPATH,"Meter Comparison AvgCons Tooltip Close"),
	
	//Promotional Messages Residential
	lbl_ResLeftHeader("#footerMsgDiv > #footerMessagesLeft > #leftMessageTable > tbody > #row_x > #leftMessageTable_MSG_LINE_1",CSS,"Residential Left Header"),
	img_ResLeftImage("#footerMsgDiv > #footerMessagesLeft > #leftMessageTable > tbody > #row_x > #leftMessageTable_IMG_LINE_2 > #img_leftMessageTable_IMG_LINE_2",CSS,"Left Message Image"),
	lbl_ResLeftMessage("#footerMsgDiv > #footerMessagesLeft > #leftMessageTable > tbody > #row_x > #leftMessageTable_MSG_LINE_2 > p",CSS,"Residential Left Message"),
	lnk_ResLeftUrl("#footerMsgDiv > #footerMessagesLeft > #leftMessageTable > tbody > #row_x > #leftMessageTable_MSG_LINE_2 > p > a",CSS,"Residential Left Message URL"),
	
	lbl_ResRightHeader("#footerMsgDiv > #footerMessagesCenter > #centerMessageTable > tbody > #row_x > #centerMessageTable_MSG_LINE_1",CSS,"Residential Center Header"),
	img_ResCenterImage("#footerMsgDiv > #footerMessagesCenter > #centerMessageTable > tbody > #row_x > #centerMessageTable_IMG_LINE_2 > #img_centerMessageTable_IMG_LINE_2",CSS,"Center Message Image"),
	lbl_ResCenterMessage("#footerMsgDiv > #footerMessagesCenter > #centerMessageTable > tbody > #row_x > #centerMessageTable_MSG_LINE_2 > p",CSS,"Residential Center  Message"),
	lnk_ResCenterUrl("#footerMsgDiv > #footerMessagesCenter > #centerMessageTable > tbody > #row_x > #centerMessageTable_MSG_LINE_2 > p > a",CSS,"Residential Center Message URL"),
	
	lbl_ResidentialRightHeader("#footerMsgDiv > #footerMessagesRight > #rightMessageTable > tbody > #row_x > #rightMessageTable_MSG_LINE_1",CSS,"Residential Right Header"),
	img_RightImage("#footerMsgDiv > #footerMessagesRight > #rightMessageTable > tbody > #row_x > #rightMessageTable_IMG_LINE_2 > #img_rightMessageTable_IMG_LINE_2",CSS,"DollarImage"),
	lbl_ResidentialRightMessage("#footerMsgDiv > #footerMessagesRight > #rightMessageTable > tbody > #row_x > #rightMessageTable_MSG_LINE_2 > p",CSS,"Residential Right Message"),
	lnk_ResRightUrl("#footerMsgDiv > #footerMessagesRight > #rightMessageTable > tbody > #row_x > #rightMessageTable_MSG_LINE_2 > p > a",CSS,"Residential Right Message URL"),
	
	//Promotional Messages Commercial
	lbl_LeftHeader("#footerMsgDiv > #footerMessagesLeft > #leftMessageTable > tbody > #row_x > #leftMessageTable_MSG_LINE_1",CSS,"Left Header"),
	img_IconArrow("#footerMsgDiv > #footerMessagesLeft > #leftMessageTable > tbody > #row_x > #leftMessageTable_IMG_LINE_2 > #img_leftMessageTable_IMG_LINE_2",CSS,"IconArrowImage"),
	lbl_LeftMessage("#footerMsgDiv > #footerMessagesLeft > #leftMessageTable > tbody > #row_x > #leftMessageTable_MSG_LINE_2 > p",CSS,"Left Message"),
	lnk_LowerBill("How it makes your bill lower",LINKTEXT,"'How it makes your bill lower' Link"),
	
	lbl_CenterHeader("#footerMsgDiv > #footerMessagesCenter > #centerMessageTable > tbody > #row_x > #centerMessageTable_MSG_LINE_1",CSS,"Power Tracker Header"),
	img_SunImage("#footerMsgDiv > #footerMessagesCenter > #centerMessageTable > tbody > #row_x > #centerMessageTable_IMG_LINE_2 > #img_centerMessageTable_IMG_LINE_2",CSS,"CheckImage"),
	lbl_CenterMessage("#footerMsgDiv > #footerMessagesCenter > #centerMessageTable > tbody > #row_x > #centerMessageTable_MSG_LINE_2 > p",CSS,"Power Tracker Message"),
	lnk_SolarAction("See solar in action near you",LINKTEXT,"'See solar in action near you' Link"),
	
	lbl_RightHeader("#footerMsgDiv > #footerMessagesRight > #rightMessageTable > tbody > #row_x > #rightMessageTable_MSG_LINE_1",CSS,"Commercial Header"),
	img_IconPointer("#footerMsgDiv > #footerMessagesRight > #rightMessageTable > tbody > #row_x > #rightMessageTable_IMG_LINE_2 > #img_rightMessageTable_IMG_LINE_2",CSS,"IconArrowImage"),
	lbl_RightMessage("#footerMsgDiv > #footerMessagesRight > #rightMessageTable > tbody > #row_x > #rightMessageTable_MSG_LINE_2 > p",CSS,"Commercial Message"),
	lnk_BlogPost("Read our blog post",LINKTEXT,"Read our blog post Link");
	
	
	String strProperty = "";
	ObjectLocator locatorType = null;
	String strObjName = "";
	
	public String getProperty(){
		return strProperty;
	}

	public ObjectLocator getLocatorType(){
		return locatorType;
	}
	public String getObjectName(){
		return strObjName;
	}

	private ViewBillPageObjects(String strPropertyValue, ObjectLocator locatorType, String strObjName){
		this.strProperty = strPropertyValue;
		this.locatorType = locatorType;
		this.strObjName = strObjName;
	}
		
}
