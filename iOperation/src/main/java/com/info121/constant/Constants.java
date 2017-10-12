package com.info121.constant;

import com.info121.model.iMessage;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static String intentKey_iOp = "intent.key.iop";
    public static String intentKey_iOp_suppliercode = "intent.key.iop.suppliercode";

    public static String APIDotNetNameSpace = "http://tempuri.org/";

    //public static String SERVER_URL = "203.125.95.177";
//	public static String SERVER_URL = "203.125.95.176";
   // public static String SERVER_URL = "118.200.199.248";
//    public static String SERVER_URL = "203.126.224.76";
   public static String SERVER_URL = "118.200.199.248";



    //public static String SERVER_URL = "203.127.123.231";
    //public static String SERVER_URL = "alexisinfo121.noip.me";

    public static String BASE_URL = "http://" + SERVER_URL;

    //public static String BASE_PHOTO_URL = "alexisinfo121.noip.me";
	public static String BASE_PHOTO_URL = "203.125.95.176";
//	public static String BASE_PHOTO_URL = "203.125.95.177";
 //   public static String BASE_PHOTO_URL = "118.200.199.248";

   // public static String BASE_PHOTO_URL = "203.126.224.76";

    public static String uRLiOPsRegisterGetCountry = BASE_URL + "/iops/service.asmx?op=IOPS_GetCountry";
    public static String APIDotNetSOAPActionGetCountry = "http://tempuri.org/IOPS_GetCountry";
    public static String APIDotNetMethodNameGetCountry = "IOPS_GetCountry";

    public static String uRLiOPsRegisterGetCity = BASE_URL + "/iops/service.asmx?op=IOPS_GetCity";
    public static String APIDotNetSOAPActionGetCity = "http://tempuri.org/IOPS_GetCity";
    public static String APIDotNetMethodNameGetCity = "IOPS_GetCity";

    public static String uRLiOPsRegisterSaveSupplier = BASE_URL + "/iops/service.asmx?op=IOPS_SaveSupplier";
    public static String APIDotNetSOAPActionSaveSupplier = "http://tempuri.org/IOPS_SaveSupplier";
    public static String APIDotNetMethodNameSaveSupplier = "IOPS_SaveSupplier";


    public static String uRLiOPsRegisterValidateSupplier = BASE_URL + "/iops/service.asmx?op=IOPS_ValidateSupplier";
    public static String APIDotNetSOAPActionValidateSupplier = "http://tempuri.org/IOPS_ValidateSupplier";
    public static String APIDotNetMethodNameValidateSupplier = "IOPS_ValidateSupplier";

    public static String uRLiOPsUserChangePassword = BASE_URL + "/iops/service.asmx?op=IOPS_User_ChangePassword";
    public static String APIDotNetSOAPActionUserChangePassword = "http://tempuri.org/IOPS_User_ChangePassword";
    public static String APIDotNetMethodNameUserChangePassword = "IOPS_User_ChangePassword";

    public static String uRLValidateIOPSUser = BASE_URL + "/iops/service.asmx?op=IOPS_ValidateIOPSUser";
    public static String APIDotNetSOAPActionValidateIOPSUser = "http://tempuri.org/IOPS_ValidateIOPSUser";
    public static String APIDotNetMethodNameValidateIOPSUser = "IOPS_ValidateIOPSUser";

    public static String uRLiOpsGetSupplierAssignedJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierAssignedJobsList";
    public static String APIDotNetSOAPActioniOpsGetSupplierAssignedJobsList = "http://tempuri.org/IOPS_GetSupplierAssignedJobsList";
    public static String APIDotNetMethodNameGetSupplierAssignedJobsList = "IOPS_GetSupplierAssignedJobsList";


    public static String uRLiOpsDriverClaimsSaveJobClaimJob = BASE_URL + "/iops/service.asmx?op=IOPS_DriverClaimsSaveJobClaimJob";
    public static String ApiDotNetSOAPActionopsDriverClaimsSaveJobClaimJob = "http://tempuri.org/IOPS_DriverClaimsSaveJobClaimJob";
    public static String APIDotNetMethodNameDriverClaimsSaveJobClaimJob = "IOPS_DriverClaimsSaveJobClaimJob";


    public static String uRLiOpsDriverClaimsGetUnclaimedJobs = BASE_URL + "/iops/service.asmx?op=IOPS_DriverClaimsGetUnclaimedJobs";
    public static String ApiDotNetSOAPActionopsDriverClaimsGetUnclaimedJobs = "http://tempuri.org/IOPS_DriverClaimsGetUnclaimedJobs";
    public static String APIDotNetMethodNameDriverClaimsGetUnclaimedJobs = "IOPS_DriverClaimsGetUnclaimedJobs";


    public static String uRLiOpsDriverClaimsGetJobClaimJob = BASE_URL + "/iops/service.asmx?op=IOPS_DriverClaimsGetJobClaimJob";
    public static String ApiDotNetSOAPActionopsDriverClaimsGetJobClaimJob = "http://tempuri.org/IOPS_DriverClaimsGetJobClaimJob";
    public static String APIDotNetMethodNameDriverClaimsGetJobClaimJob = "IOPS_DriverClaimsGetJobClaimJob";


    public static String uRLiOpsGetSupplierJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierJobsList";
    public static String APIDotNetSOAPActioniOpsGetSupplierJobsList = "http://tempuri.org/IOPS_GetSupplierJobsList";
    public static String APIDotNetMethodNameGetSupplierJobsList = "IOPS_GetSupplierJobsList";

    public static String uRLiOpsGetSupplierAcceptedJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierAcceptedJobsList";
    public static String APIDotNetSOAPActioniOpsGetSupplierAcceptedJobsList = "http://tempuri.org/IOPS_GetSupplierAcceptedJobsList";
    public static String APIDotNetMethodNameGetSupplierAceptedJobsList = "IOPS_GetSupplierAcceptedJobsList";

    public static String uRLiOpsGetSupplierRejectedJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierRejectedJobsList";
    public static String APIDotNetSOAPActioniOpsGetSupplierRejectedJobsList = "http://tempuri.org/IOPS_GetSupplierRejectedJobsList";
    public static String APIDotNetMethodNameGetSupplierRejectedJobsList = "IOPS_GetSupplierRejectedJobsList";

    public static String uRLiOpsGetSupplierAssignedDailyJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierAssignedDailyJobsList";
    public static String APIDotNetSOAPActioniOpsGetSupplierAssignedDailyJobsList = "http://tempuri.org/IOPS_GetSupplierAssignedDailyJobsList";
    public static String APIDotNetMethodNameGetSupplierAssignedDailyJobsList = "IOPS_GetSupplierAssignedDailyJobsList";


//	public static String uRLiOpsGetSupplierBidList = BASE_URL+"/iops/service.asmx?op=IOPS_GetSupplierBidsList";
//	public static String APIDotNetSOAPActioniOpsGetSupplierBidList = "http://tempuri.org/IOPS_GetSupplierBidsList";
//	public static String APIDotNetMethodNameGetSupplierBidList = "IOPS_GetSupplierBidsList";

    public static String uRLiOpsGetSupplierBidNewList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierBidNewList";
    public static String APIDotNetSOAPActioniOpsGetSupplierBidNewList = "http://tempuri.org/IOPS_GetSupplierBidNewList";
    public static String APIDotNetMethodNameGetSupplierBidNewList = "IOPS_GetSupplierBidNewList";

    public static String uRLiOpsGetSupplierBidAcceptList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierBidAcceptList";
    public static String APIDotNetSOAPActioniOpsGetSupplierBidAcceptList = "http://tempuri.org/IOPS_GetSupplierBidAcceptList";
    public static String APIDotNetMethodNameGetSupplierBidAcceptList = "IOPS_GetSupplierBidAcceptList";

    public static String uRLiOpsGetSupplierBidRejectedList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierBidRejectedList";
    public static String APIDotNetSOAPActioniOpsGetSupplierBidRejectedList = "http://tempuri.org/IOPS_GetSupplierBidRejectedList";
    public static String APIDotNetMethodNameGetSupplierBidRejectedList = "IOPS_GetSupplierBidRejectedList";

    public static String uRLiOpsGetSupplierBidCloseList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierBidCloseList";
    public static String APIDotNetSOAPActioniOpsGetSupplierBidCloseList = "http://tempuri.org/IOPS_GetSupplierBidCloseList";
    public static String APIDotNetMethodNameGetSupplierBidCloseList = "IOPS_GetSupplierBidCloseList";

    public static String uRLiOpsSaveFeedBack = BASE_URL + "/iops/service.asmx?op=IOPS_SaveFeedBack";
    public static String APIDotNetSOAPActioniOpsSaveFeedback = "http://tempuri.org/IOPS_SaveFeedBack";
    public static String APIDotNetMethodNameSaveFeedBack = "IOPS_SaveFeedBack";

    public static String uRLiOpsGetSupplierUserListToAssigned = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierUserListToAssigned";
    public static String APIDotNetSOAPActioniOpsGetSupplierUserListToAssigned = "http://tempuri.org/IOPS_GetSupplierUserListToAssigned";
    public static String APIDotNetMethodNameGetSupplierUserListToAssigned = "IOPS_GetSupplierUserListToAssigned";

    public static String uRLiOpsUpdateJobAssignedPerson = BASE_URL + "/iops/service.asmx?op=IOPS_Update_Job_Assigned_Person";
    public static String APIDotNetSOAPActioniOpsUpdateJobAssignedPerson = "http://tempuri.org/IOPS_Update_Job_Assigned_Person";
    public static String APIDotNetMethodNameUpdateJobAssignedPerson = "IOPS_Update_Job_Assigned_Person";

    public static String uRLiOpsUpdateJobAcceptedStatus = BASE_URL + "/iops/service.asmx?op=IOPS_Update_JobAcceptedStatus";
    public static String APIDotNetSOAPActioniOpsUpdateJobAcceptedStatus = "http://tempuri.org/IOPS_Update_JobAcceptedStatus";
    public static String APIDotNetMethodNameUpdateJobAcceptedStatus = "IOPS_Update_JobAcceptedStatus";

    public static String uRLiOpsUpdateJobRejectedStatus = BASE_URL + "/iops/service.asmx?op=IOPS_Update_JobRejectedStatus";
    public static String APIDotNetSOAPActioniOpsUpdateJobRejectedStatus = "http://tempuri.org/IOPS_Update_JobRejectedStatus";
    public static String APIDotNetMethodNameUpdateJobRejectedStatus = "IOPS_Update_JobRejectedStatus";


    // details of listing api

    public static String uRLIOPS_DriverClaimsGetDetailedClaimListing = BASE_URL + "/iops/service.asmx?op=IOPS_DriverClaimsGetDetailedClaimListing";
    public static String APIDotNetSOAPActionIOPS_DriverClaimsGetDetailedClaimListing = "http://tempuri.org/IOPS_DriverClaimsGetDetailedClaimListing";
    public static String APIDotNetMethodNameUpdateClaimsGetDetailedClaimListing = "IOPS_DriverClaimsGetDetailedClaimListing";




    // IOPS_DriverClaimsUpdateJob  upate claim amount in listing details screen

    public static String uRLIOPS_DriverClaimsUpdateJob = BASE_URL + "/iops/service.asmx?op=IOPS_DriverClaimsUpdateJob";
    public static String APIDotNetSOAPActionIOPS_DriverClaimsUpdateJob = "http://tempuri.org/IOPS_DriverClaimsUpdateJob";
    public static String APIDotNetMethodNameUpdateClaimsUpdateJob = "IOPS_DriverClaimsUpdateJob";





    // listing api billing screen

    public static String uRLIOPS_DriverClaimsGetClaimListing = BASE_URL + "/iops/service.asmx?op=IOPS_DriverClaimsGetClaimListing";
    public static String APIDotNetSOAPActionIOPS_DriverClaimsGetClaimListing = "http://tempuri.org/IOPS_DriverClaimsGetClaimListing";
    public static String APIDotNetMethodNameClaimsGetClaimListing = "IOPS_DriverClaimsGetClaimListing";



    // get events for calendar

    public static String uRLIOPS_GetJobsForCalendar = BASE_URL + "/iops/service.asmx?op=GetJobsForCalendar";
    public static String APIDotNetSOAPActionIOPS_GetJobsForCalendar = "http://tempuri.org/GetJobsForCalendar";
    public static String APIDotNetMethodNameGetJobsForCalendar= "GetJobsForCalendar";


    // get generated claim no api


    public static String uRLIOPS_DriverClaimsGetGeneratedClaimNo = BASE_URL + "/iops/service.asmx?op=IOPS_DriverClaimsGetGeneratedClaimNo";
    public static String APIDotNetSOAPActionIOPS_DriverClaimsGetGeneratedClaimNo = "http://tempuri.org/IOPS_DriverClaimsGetGeneratedClaimNo";
    public static String APIDotNetMethodNameClaimsGetGeneratedClaimNo = "IOPS_DriverClaimsGetGeneratedClaimNo";


// save IOPS_DriverClaimsSaveJob

    public static String uRLIOPS_DriverClaimsSaveJob = BASE_URL + "/iops/service.asmx?op=IOPS_DriverClaimsSaveJob";
    public static String APIDotNetSOAPActionIOPS_DriverClaimsSaveJob = "http://tempuri.org/IOPS_DriverClaimsSaveJob";
    public static String APIDotNetMethodNameDriverClaimsSaveJob = "IOPS_DriverClaimsSaveJob";


    public static String uRLIOPS_DriverClaimsListJobsForClaim = BASE_URL + "/iops/service.asmx?op=IOPS_DriverClaimsListJobsForClaim";
    public static String APIDotNetSOAPActionIOPS_DriverClaimsListJobsForClaim = "http://tempuri.org/IOPS_DriverClaimsListJobsForClaim";
    public static String APIDotNetMethodNameDriverClaimsListJobsForClaim = "IOPS_DriverClaimsListJobsForClaim";


    public static String uRLiOpsUpdateBidAcceptedStatus = BASE_URL + "/iops/service.asmx?op=IOPS_Update_BidAcceptedStatus";
    public static String APIDotNetSOAPActioniOpsUpdateBidAcceptedStatus = "http://tempuri.org/IOPS_Update_BidAcceptedStatus";
    public static String APIDotNetMethodNameUpdateBidAcceptedStatus = "IOPS_Update_BidAcceptedStatus";

    public static String uRLiOpsUpdateBidRejectedStatus = BASE_URL + "/iops/service.asmx?op=IOPS_Update_BidRejectedStatus";
    public static String APIDotNetSOAPActioniOpsUpdateBidRejectedStatus = "http://tempuri.org/IOPS_Update_BidRejectedStatus";
    public static String APIDotNetMethodNameUpdateBidRejectedStatus = "IOPS_Update_BidRejectedStatus";

    public static String uRLiOpsSaveShowPassenger = BASE_URL + "/iops/service.asmx?op=IOPS_SaveShowPassenger";
    public static String APIDotNetSOAPActioniOpsSaveShowPassenger = "http://tempuri.org/IOPS_SaveShowPassenger";
    public static String APIDotNetMethodNameSaveShowPassenger = "IOPS_SaveShowPassenger";

    public static String uRLiOpsSaveNoShowPassenger = BASE_URL + "/iops/service.asmx?op=IOPS_SaveNoShowPassenger";
    public static String APIDotNetSOAPActioniOpsSaveNoShowPassenger = "http://tempuri.org/IOPS_SaveNoShowPassenger";
    public static String APIDotNetMethodNameSaveNoShowPassenger = "IOPS_SaveNoShowPassenger";

    public static String uRLiOpsIsSupplierMainContactPerson = BASE_URL + "/iops/service.asmx?op=eNoIOPS_IsSupplierMainContactPerson";
    public static String APIDotNetSOAPActioniOpsIsSupplierMainContactPerson = "http://tempuri.org/IOPS_IsSupplierMainContactPerson";
    public static String APIDotNetMethodNameIsSupplierMainContactPerson = "IOPS_IsSupplierMainContactPerson";

    //	public static String uRLIOPS_GetSupplierShowAndServedJobsList = BASE_URL+"/iops/service.asmx?op=IOPS_GetSupplierShowAndServedJobsList";
    public static String uRLIOPS_GetSupplierShowAndServedJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierServedJobsList";

    public static String APIDotNetSOAPActionIOPS_GetSupplierShowAndServedJobsList = "http://tempuri.org/IOPS_GetSupplierShowAndServedJobsList";
    public static String APIDotNetMethodNameIOPS_GetSupplierShowAndServedJobsList = "IOPS_GetSupplierShowAndServedJobsList";

    //	public static String uRLIOPS_GetSupplierNoShowAndServedJobsList = BASE_URL+"/iops/service.asmx?op=IOPS_GetSupplierNoShowAndServedJobsList";
    public static String uRLIOPS_GetSupplierNoShowAndServedJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierNoShowAndServedJobsList";
    public static String APIDotNetSOAPActionIOPS_GetSupplierNoShowAndServedJobsList = "http://tempuri.org/IOPS_GetSupplierNoShowAndServedJobsList";
    public static String APIDotNetMethodNameIOPS_GetSupplierNoShowAndServedJobsList = "IOPS_GetSupplierNoShowAndServedJobsList";

    public static String uRLIOPS_GetSupplierRejectedJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierRejectedJobsList";
    public static String APIDotNetSOAPActionIOPS_GetSupplierRejectedJobsList = "http://tempuri.org/IOPS_GetSupplierRejectedJobsList";
    public static String APIDotNetMethodNameIOPS_GetSupplierRejectedJobsList = "IOPS_GetSupplierRejectedJobsList";


    // KZHTUN on 20171025
    public static String uRLiOpsGetDriverAllMessages = BASE_URL + "/iops/service.asmx?op=IOPS_Messages_GetDriverAllMessages";
    public static String APIDotNetSOAPActioniOpsGetDriverAllMessages = "http://tempuri.org/IOPS_Messages_GetDriverAllMessages";
    public static String APIDotNetMethodNameGetDriverAllMessages = "IOPS_Messages_GetDriverAllMessages";

    // KZHTUN on 20171025
    public static String uRLiOpsGetDriverMessages = BASE_URL + "/iops/service.asmx?op=IOPS_Messages_GetDriverMessageByID";
    public static String APIDotNetSOAPActioniOpsGetDriverMessages = "http://tempuri.org/IOPS_Messages_GetDriverMessageByID";
    public static String APIDotNetMethodNameGetDriverMessages = "IOPS_Messages_GetDriverMessageByID";

    // KZHTUN on 20171025
    public static String uRLiOpsUpdateDriverMessages = BASE_URL + "/iops/service.asmx?op=IOPS_Messages_UpdateStatus";
    public static String APIDotNetSOAPActioniOpsUpdateDriverMessages = "http://tempuri.org/IOPS_Messages_UpdateStatus";
    public static String APIDotNetMethodNameUpdateDriverMessages = "IOPS_Messages_UpdateStatus";

    // KZHTUN on 20171025
    public static String uRLiOpsDeleteDriverMessages = BASE_URL + "/iops/service.asmx?op=IOPS_Messages_Delete";
    public static String APIDotNetSOAPActioniOpsDeleteDriverMessages = "http://tempuri.org/IOPS_Messages_Delete";
    public static String APIDotNetMethodNameDeleteDriverMessages = "IOPS_Messages_Delete";

    // KZHTUN on 20171110
    public static String uRLiOpsGetDriverAllJobs = BASE_URL + "/iops/service.asmx?op=IOPS_Survey_ListJobs";
    public static String APIDotNetSOAPActioniOpsGetDriverAllJobs = "http://tempuri.org/IOPS_Survey_ListJobs";
    public static String APIDotNetMethodNameGetDriverAllJobs = "IOPS_Survey_ListJobs";

    // KZHTUN on 20171110
    public static String uRLiOpsSetSurveyInfo = BASE_URL + "/iops/service.asmx?op=IOPS_Survey_Start";
    public static String APIDotNetSOAPActioniOpsSetSurveyInfo = "http://tempuri.org/IOPS_Survey_Start";
    public static String APIDotNetMethodNameSetSurveyInfo = "IOPS_Survey_Start";
//
//    // KZHTUN on 20171025
//    public static String uRLiOpsGetDriverNewMessages = BASE_URL + "/iops/service.asmx?op=IOPS_Messages_GetDriverNewMessages";
//    public static String APIDotNetSOAPActioniOpsGetDriverNewMessages = "http://tempuri.org/IOPS_Messages_GetDriverNewMessages";
//    public static String APIDotNetMethodNameGetDriverNewMessages = "IOPS_Messages_GetDriverNewMessages";

    public static List<iMessage> AllMessages = new ArrayList<>();
    public static int unreadCount = 0;


    public static void reload() {


        uRLiOPsRegisterGetCountry = BASE_URL + "/iops/service.asmx?op=IOPS_GetCountry";
        APIDotNetSOAPActionGetCountry = "http://tempuri.org/IOPS_GetCountry";
        APIDotNetMethodNameGetCountry = "IOPS_GetCountry";

        uRLiOPsRegisterGetCity = BASE_URL + "/iops/service.asmx?op=IOPS_GetCity";
        APIDotNetSOAPActionGetCity = "http://tempuri.org/IOPS_GetCity";
        APIDotNetMethodNameGetCity = "IOPS_GetCity";

        uRLiOPsRegisterSaveSupplier = BASE_URL + "/iops/service.asmx?op=IOPS_SaveSupplier";
        APIDotNetSOAPActionSaveSupplier = "http://tempuri.org/IOPS_SaveSupplier";
        APIDotNetMethodNameSaveSupplier = "IOPS_SaveSupplier";

        uRLiOPsRegisterValidateSupplier = BASE_URL + "/iops/service.asmx?op=IOPS_ValidateSupplier";
        APIDotNetSOAPActionValidateSupplier = "http://tempuri.org/IOPS_ValidateSupplier";
        APIDotNetMethodNameValidateSupplier = "IOPS_ValidateSupplier";

        uRLiOPsUserChangePassword = BASE_URL + "/iops/service.asmx?op=IOPS_User_ChangePassword";
        APIDotNetSOAPActionUserChangePassword = "http://tempuri.org/IOPS_User_ChangePassword";
        APIDotNetMethodNameUserChangePassword = "IOPS_User_ChangePassword";

        uRLValidateIOPSUser = BASE_URL + "/iops/service.asmx?op=IOPS_ValidateIOPSUser";
        APIDotNetSOAPActionValidateIOPSUser = "http://tempuri.org/IOPS_ValidateIOPSUser";
        APIDotNetMethodNameValidateIOPSUser = "IOPS_ValidateIOPSUser";

        uRLiOpsGetSupplierAssignedJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierAssignedJobsList";
        APIDotNetSOAPActioniOpsGetSupplierAssignedJobsList = "http://tempuri.org/IOPS_GetSupplierAssignedJobsList";
        APIDotNetMethodNameGetSupplierAssignedJobsList = "IOPS_GetSupplierAssignedJobsList";

        uRLiOpsGetSupplierJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierJobsList";
        APIDotNetSOAPActioniOpsGetSupplierJobsList = "http://tempuri.org/IOPS_GetSupplierJobsList";
        APIDotNetMethodNameGetSupplierJobsList = "IOPS_GetSupplierJobsList";

        uRLiOpsGetSupplierAcceptedJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierAcceptedJobsList";
        APIDotNetSOAPActioniOpsGetSupplierAcceptedJobsList = "http://tempuri.org/IOPS_GetSupplierAcceptedJobsList";
        APIDotNetMethodNameGetSupplierAceptedJobsList = "IOPS_GetSupplierAcceptedJobsList";

        uRLiOpsGetSupplierRejectedJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierRejectedJobsList";
        APIDotNetSOAPActioniOpsGetSupplierRejectedJobsList = "http://tempuri.org/IOPS_GetSupplierRejectedJobsList";
        APIDotNetMethodNameGetSupplierRejectedJobsList = "IOPS_GetSupplierRejectedJobsList";

        uRLiOpsGetSupplierAssignedDailyJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierAssignedDailyJobsList";
        APIDotNetSOAPActioniOpsGetSupplierAssignedDailyJobsList = "http://tempuri.org/IOPS_GetSupplierAssignedDailyJobsList";
        APIDotNetMethodNameGetSupplierAssignedDailyJobsList = "IOPS_GetSupplierAssignedDailyJobsList";

        uRLiOpsGetSupplierBidNewList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierBidNewList";
        APIDotNetSOAPActioniOpsGetSupplierBidNewList = "http://tempuri.org/IOPS_GetSupplierBidNewList";
        APIDotNetMethodNameGetSupplierBidNewList = "IOPS_GetSupplierBidNewList";

        uRLiOpsGetSupplierBidAcceptList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierBidAcceptList";
        APIDotNetSOAPActioniOpsGetSupplierBidAcceptList = "http://tempuri.org/IOPS_GetSupplierBidAcceptList";
        APIDotNetMethodNameGetSupplierBidAcceptList = "IOPS_GetSupplierBidAcceptList";

        uRLiOpsGetSupplierBidRejectedList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierBidRejectedList";
        APIDotNetSOAPActioniOpsGetSupplierBidRejectedList = "http://tempuri.org/IOPS_GetSupplierBidRejectedList";
        APIDotNetMethodNameGetSupplierBidRejectedList = "IOPS_GetSupplierBidRejectedList";

        uRLiOpsGetSupplierBidCloseList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierBidCloseList";
        APIDotNetSOAPActioniOpsGetSupplierBidCloseList = "http://tempuri.org/IOPS_GetSupplierBidCloseList";
        APIDotNetMethodNameGetSupplierBidCloseList = "IOPS_GetSupplierBidCloseList";

        uRLiOpsSaveFeedBack = BASE_URL + "/iops/service.asmx?op=IOPS_SaveFeedBack";
        APIDotNetSOAPActioniOpsSaveFeedback = "http://tempuri.org/IOPS_SaveFeedBack";
        APIDotNetMethodNameSaveFeedBack = "IOPS_SaveFeedBack";

        uRLiOpsGetSupplierUserListToAssigned = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierUserListToAssigned";
        APIDotNetSOAPActioniOpsGetSupplierUserListToAssigned = "http://tempuri.org/IOPS_GetSupplierUserListToAssigned";
        APIDotNetMethodNameGetSupplierUserListToAssigned = "IOPS_GetSupplierUserListToAssigned";

        uRLiOpsUpdateJobAssignedPerson = BASE_URL + "/iops/service.asmx?op=IOPS_Update_Job_Assigned_Person";
        APIDotNetSOAPActioniOpsUpdateJobAssignedPerson = "http://tempuri.org/IOPS_Update_Job_Assigned_Person";
        APIDotNetMethodNameUpdateJobAssignedPerson = "IOPS_Update_Job_Assigned_Person";

        uRLiOpsUpdateJobAcceptedStatus = BASE_URL + "/iops/service.asmx?op=IOPS_Update_JobAcceptedStatus";
        APIDotNetSOAPActioniOpsUpdateJobAcceptedStatus = "http://tempuri.org/IOPS_Update_JobAcceptedStatus";
        APIDotNetMethodNameUpdateJobAcceptedStatus = "IOPS_Update_JobAcceptedStatus";

        uRLiOpsUpdateJobRejectedStatus = BASE_URL + "/iops/service.asmx?op=IOPS_Update_JobRejectedStatus";
        APIDotNetSOAPActioniOpsUpdateJobRejectedStatus = "http://tempuri.org/IOPS_Update_JobRejectedStatus";
        APIDotNetMethodNameUpdateJobRejectedStatus = "IOPS_Update_JobRejectedStatus";

        uRLiOpsUpdateBidAcceptedStatus = BASE_URL + "/iops/service.asmx?op=IOPS_Update_BidAcceptedStatus";
        APIDotNetSOAPActioniOpsUpdateBidAcceptedStatus = "http://tempuri.org/IOPS_Update_BidAcceptedStatus";
        APIDotNetMethodNameUpdateBidAcceptedStatus = "IOPS_Update_BidAcceptedStatus";

        uRLiOpsUpdateBidRejectedStatus = BASE_URL + "/iops/service.asmx?op=IOPS_Update_BidRejectedStatus";
        APIDotNetSOAPActioniOpsUpdateBidRejectedStatus = "http://tempuri.org/IOPS_Update_BidRejectedStatus";
        APIDotNetMethodNameUpdateBidRejectedStatus = "IOPS_Update_BidRejectedStatus";

        uRLiOpsSaveShowPassenger = BASE_URL + "/iops/service.asmx?op=IOPS_SaveShowPassenger";
        APIDotNetSOAPActioniOpsSaveShowPassenger = "http://tempuri.org/IOPS_SaveShowPassenger";
        APIDotNetMethodNameSaveShowPassenger = "IOPS_SaveShowPassenger";

        uRLiOpsSaveNoShowPassenger = BASE_URL + "/iops/service.asmx?op=IOPS_SaveNoShowPassenger";
        APIDotNetSOAPActioniOpsSaveNoShowPassenger = "http://tempuri.org/IOPS_SaveNoShowPassenger";
        APIDotNetMethodNameSaveNoShowPassenger = "IOPS_SaveNoShowPassenger";

        uRLiOpsIsSupplierMainContactPerson = BASE_URL + "/iops/service.asmx?op=eNoIOPS_IsSupplierMainContactPerson";
        APIDotNetSOAPActioniOpsIsSupplierMainContactPerson = "http://tempuri.org/IOPS_IsSupplierMainContactPerson";
        APIDotNetMethodNameIsSupplierMainContactPerson = "IOPS_IsSupplierMainContactPerson";

        uRLIOPS_GetSupplierShowAndServedJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierShowAndServedJobsList";
        APIDotNetSOAPActionIOPS_GetSupplierShowAndServedJobsList = "http://tempuri.org/IOPS_GetSupplierShowAndServedJobsList";
        APIDotNetMethodNameIOPS_GetSupplierShowAndServedJobsList = "IOPS_GetSupplierShowAndServedJobsList";

        uRLIOPS_GetSupplierNoShowAndServedJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierNoShowAndServedJobsList";
        APIDotNetSOAPActionIOPS_GetSupplierNoShowAndServedJobsList = "http://tempuri.org/IOPS_GetSupplierNoShowAndServedJobsList";
        APIDotNetMethodNameIOPS_GetSupplierNoShowAndServedJobsList = "IOPS_GetSupplierNoShowAndServedJobsList";

        uRLIOPS_GetSupplierRejectedJobsList = BASE_URL + "/iops/service.asmx?op=IOPS_GetSupplierRejectedJobsList";
        APIDotNetSOAPActionIOPS_GetSupplierRejectedJobsList = "http://tempuri.org/IOPS_GetSupplierRejectedJobsList";
        APIDotNetMethodNameIOPS_GetSupplierRejectedJobsList = "IOPS_GetSupplierRejectedJobsList";


    }
}
