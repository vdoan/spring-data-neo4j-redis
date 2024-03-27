package com.tutorial.rediscache.dao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityLabels {
    //ENTITY
    public static final String Ad = "Ad";
    public static final String Brand = "Brand";
    public static final String Button = "Button";
    public static final String Customer = "Customer";
    public static final String Drive = "Drive";
    public static final String Interest = "Interest";
    public static final String Party = "Party";
    public static final String Group = "Group";
    public static final String User = "User";
    public static final String People = "People";
    public static final String Friend = "Friend";
    public static final String Company = "Company";
    public static final String CompanyDepartment = "CompanyDepartment";
    public static final String CompanyEmployee = "CompanyEmployee";
    public static final String Institute = "Institute";
    public static final String Page = "Page";
    public static final String Shop = "Shop";
    public static final String Product = "Product";
    public static final String ProductDimension = "ProductDimension";
    public static final String Notification = "Notification";
    public static final String Service = "Service";
    public static final String ProductVariant = "ProductVariant";
    public static final String VariantOption = "VariantOption";
    public static final String Variant = "Variant";
    public static final String City = "City";
    public static final String District = "District";
    public static final String State = "State";
    public static final String Country = "Country";
    public static final String Contact = "Contact";
    public static final String PartyContact = "PartyContact";
    public static final String CustomerContact = "CustomerContact";

    public static final String Inventory = "Inventory";

    public static final String Project = "Project";
    public static final String Link = "Link";
    public static final String PartyLink = "PartyLink";
    public static final String PartyTransfer = "PartyTransfer";
    public static final String PartyLanguage= "PartyLanguage";

    public static final String Comment = "Comment";
    public static final String Role = "RoleType";

    public static final String PartyCustomer = "PartyCustomer";

    public static final String PartyPreference = "PartyPreference";
    public static final String PartyPrivacy = "PartyPrivacy";
    public static final String PostalAddress = "PostalAddress";
    public static final String TextResource = "TextResource";
    public static final String ImageResource = "ImageResource";
    public static final String AdResource = "AdResource";
    public static final String AdResourceItem = "AdResourceItem";
    public static final String DataResource = "DataResource";
    public static final String Resource = "Resource";
    public static final String ExternalResource = "ExternalResource";
    public static final String LinkResource = "LinkResource";
    public static final String Place = "Place";
    public static final String SaleResource = "SaleResource";
    public static final String VideoResource = "VideoResource";
    public static final String Reaction = "Reaction";
    public static final String Save = "Save";
    public static final String Report = "Report";
    public static final String Share = "Share";
    public static final String Announcement = "Announcement";
    public static final String DataResourcePolicy = "DataResourcePolicy";
    public static final String ConnectionPolicy = "ConnectionPolicy";
    public static final String GroupPolicy = "GroupPolicy";
    public static final String Invitation = "Invitation";
    public static final String WorkHistory = "WorkHistory";
    public static final String PartyPolicy = "PartyPolicy";
    public static final String PartyGroupPolicy = "PartyGroupPolicy";
    public static final String PartyPagePolicy = "PartyPagePolicy";
    public static final String AppointmentPolicy = "AppointmentPolicy";

    public static final String Topic = "Topic";
    public static final String HashTag = "HashTag";

    public static final String Industry = "Industry";
    public static final String IndustryLocale = "IndustryLocale";

    public static final String Category = "Category";
    public static final String CategoryLocale = "CategoryLocale";
    public static final String CategoryReviewField = "CategoryReviewField";
    public static final String CategorySpecification = "CategorySpecification";


    public static final String Department = "Department";
    public static final String Employee = "Employee";

    public static final String Field = "Field";
    public static final String FieldLocale = "FieldLocale";
    public static final String EmploymentTypeLocale = "EmploymentTypeLocale";

    public static final String JobFunction = "JobFunction";
    public static final String JobFunctionLocale = "JobFunctionLocale";

    public static final String Nationality = "Nationality";
    public static final String NationalityLocale = "NationalityLocale";

    public static final String Skill = "Skill";
    public static final String SkillLocale = "SkillLocale";

    public static final String Survey = "Survey";
    public static final String SurveyOption = "SurveyOption";
    public static final String AvailableOption = "AvailableOption";
    public static final String BusinessHour = "BusinessHour";
    public static final String AvailableDay = "AvailableDay";

    public static final String JobPreference = "JobPreference";
    public static final String JobLocation = "JobLocation";

    public static final String NotificationPreference = "NotificationPreference";
    public static final String NotificationProfile = "NotificationProfile";
    public static final String NotificationJob = "NotificationJob";
    public static final String NotificationCalendar= "NotificationCalendar";
    public static final String NotificationNetwork = "NotificationNetwork";
    public static final String NotificationFeed = "NotificationFeed";
    public static final String NotificationMessage = "NotificationMessage";

    public static final String RequestChangeOwner = "RequestChangeOwner";
    public static final String UserThread = "UserThread";
    public static final String MessageThread = "MessageThread";
    public static final String Message = "Message";

    public static final String UserSkill = "UserSkill";

    public static final String Source = "Source";
    public static final String Article = "Article";

    public static final String Folder = "Folder";
    public static final String File = "File";
    public static final String Resume = "Resume";

    public static final String Review = "Review";
    public static final String CompanyReview = "CompanyReview";
    public static final String InstituteReview = "InstituteReview";
    public static final String ShopReview = "ShopReview";
    public static final String ProductReview = "ProductReview";

    public static final String PartySubscription = "PartySubscription";
    public static final String Subscription = "Subscription";

    //RELATIONSHIPS
    public static final String USER_CONTACT="USER_CONTACT";
    public static final String CUSTOMER_OF="CUSTOMER_OF";
    public static final String CATEGORY="CATEGORY";
    public static final String INTEREST="INTEREST";
    public static final String INDUSTRY="INDUSTRY";
    public static final String JOB_FUNCTION="JOB_FUNCTION";
    public static final String CONTENT="CONTENT";
    public static final String ADDRESS = "ADDRESS";
    public static final String PREFERENCES = "PREFERENCES";
    public static final String PRIVACY = "PRIVACY";
    public static final String RESOURCE = "RESOURCE";

    public static final String POST_IMAGPRIVACYE="POST_IMAGE";

    public static final String WORKS_FOR="WORKS_FOR";
    public static final String WORKED_FOR="WORKED_FOR";
    public static final String EDUCATES_AT="EDUCATES_AT";
    public static final String EDUCATED_AT="EDUCATED_AT";
    public static final String WENT_TO="WENT_TO";
    public static final String HAS_SKILL="HAS_SKILL";

    public static final String HAS_ENDORSED="HAS_ENDORSED";
    public static final String HAS_SUBSCRIPTION="HAS_SUBSCRIPTION";
    public static final String SUBSCRIPTION="SUBSCRIPTION";

    public static final String LANGUAGE = "LANGUAGE";
    public static final String REQUESTED = "REQUESTED";
    public static final String COMMENTED = "COMMENTED";
    public static final String ON = "ON";
    public static final String TEXT_TYPE = "TEXT_TYPE";
    public static final String IMAGE_TYPE = "IMAGE_TYPE";
    public static final String POSTED = "POSTED";
    public static final String POSTED_TO = "POSTED_TO";
    public static final String SAVED_POST = "SAVED_POST";
    public static final String TAGGED="TAGGED";
    public static final String HAS_POSTED = "HAS_POSTED";
    public static final String CHECKED_IN = "CHECKED_IN";
    public static final String VIDEO_TYPE = "VIDEO_TYPE";
    public static final String INTERNAL_TYPE = "INTERNAL_TYPE";
    public static final String EXTERNAL_TYPE = "EXTERNAL_TYPE";
    public static final String SALE_TYPE = "SALE_TYPE";
    public static final String REACTED = "REACTED";
    public static final String TO = "TO";
    public static final String LAST_POSTED = "LAST_POSTED";
    public static final String PREVIOUS = "PREVIOUS";

    public static final String MEMBER_OF="MEMBER_OF";
    public static final String REPORTS_TO="REPORTS_TO";

    public static final String NEXT = "NEXT";

    public static final String LAST_COMMENTED = "LAST_COMMENTED";
    public static final String FIRST_COMMENTED = "FIRST_COMMENTED";
    public static final String LAST_SAVED = "LAST_SAVED";
    public static final String IS = "IS";
    public static final String SAVED = "SAVED";

    public static final String ARCHIVED = "ARCHIVED";
    public static final String REPORTED = "REPORTED";
    public static final String SHARED = "SHARED";
    public static final String HIDE = "HIDE";

    public static final String PROFILE_CLAIMED = "PROFILE_CLAIMED";
    public static final String APPOINTMENT_POLICY = "APPOINTMENT_POLICY";
    public static final String POLICY = "POLICY";
    public static final String HAS = "HAS";
    public static final String FOLLOWED = "FOLLOWED";
    public static final String FOLLOWED_CATEGORY = "FOLLOWED_CATEGORY";
    public static final String LIKED = "LIKED";
    public static final String JOINED = "JOINED";
    public static final String INVITED = "INVITED";
    public static final String INVITED_MEMBER = "INVITED_MEMBER";
    public static final String CONNECTED = "CONNECTED";
    public static final String FRIEND = "FRIEND";
    public static final String BLOCKED = "BLOCKED";
    public static final String BLOCKED_MESSAGE = "BLOCKED_MESSAGE";
    public static final String VIEWED = "VIEWED";
    public static final String VIEWERS = "VIEWERS";
    public static final String NOTIFICATION_POST = "NOTIFICATION_POST";
    public static final String NOTIFICATION = "NOTIFICATION";


    public static final String CREATED="CREATED";
    public static final String PUBLISHED="PUBLISHED";

    public static final String CHILD="CHILD";
    public static final String TAG_TOPIC="TAG_TOPIC";
    public static final String POST_MENTIONED="POST_MENTIONED";
    public static final String POST_HASHTAG="POST_HASHTAG";

    public static final String ADRESOURCE_ITEM="ADRESOURCE_ITEM";
    public static final String PRODUCT_ADVERTISED="PRODUCT_ADVERTISED";

    public static final String BUSINESS_HOUR="BUSINESS_HOUR";
    public static final String AVAILABLE_DAY="AVAILABLE_DAY";

    public static final String SURVEY_CATEGORY="SURVEY_CATEGORY";
    public static final String SURVEY_OPTION="SURVEY_OPTION";
    public static final String AVAILABLE_OPTION="AVAILABLE_OPTION";
    public static final String SURVEY_OPTION_SELECTED="SURVEY_OPTION_SELECTED";
    public static final String VOTED="VOTED";

    public static final String CREATED_PAGE="CREATED_PAGE";
    public static final String LINKED_PAGE="LINKED_PAGE";

    public static final String PARTY_TRANSFER = "PARTY_TRANSFER";
    public static final String TRANSFER_FROM = "TRANSFER_FROM";
    public static final String TRANSFER_TO = "TRANSFER_TO";
    public static final String HAS_JOB_FUNCTION = "HAS_JOB_FUNCTION";


    public static final String HAS_LINK = "HAS_LINK";
    public static final String PARTY_LINK = "PARTY_LINK";

    public static final String HAS_CONTACT = "HAS_CONTACT";
    public static final String PARTY_CONTACT = "PARTY_CONTACT";

    public static final String HAS_INVENTORY="HAS_INVENTORY";
    public static final String INVENTORY_PRODUCT = "INVENTORY_PRODUCT";

    public static final String PRODUCT = "PRODUCT";
    public static final String PRODUCT_BRAND = "PRODUCT_BRAND";
    public static final String CREATED_PRODUCT = "CREATED_PRODUCT";
    public static final String HAS_VARIANT = "HAS_VARIANT";
    public static final String HAS_SPECIFICATIONS = "HAS_SPECIFICATIONS";
    public static final String PRODUCT_DIMENSION = "PRODUCT_DIMENSION";
    public static final String PRODUCT_VARIANT = "PRODUCT_VARIANT";
    public static final String VARIANT_OPTION = "VARIANT_OPTION";
    public static final String PRODUCT_VARIANT_OPTION = "PRODUCT_VARIANT_OPTION";
    public static final String PRODUCT_LIKED = "PRODUCT_LIKED";
    public static final String PRODUCT_INQUIRED = "PRODUCT_INQUIRED";


    public static final String LOCALE="LOCALE";

    public static final String REQUEST_CHANGE_OWNER = "REQUEST_CHANGE_OWNER";
    public static final String REQUESTER = "REQUESTER";
    public static final String RECEIVER = "RECEIVER";

    public static final String RECOMMENDED="RECOMMENDED";
    public static final String REVIEWED="REVIEWED";

    public static final String MESSAGE_THREAD = "MESSAGE_THREAD";
    public static final String THREAD_MEMBER = "THREAD_MEMBER";
    public static final String THREAD_MESSAGE = "THREAD_MESSAGE";
    public static final String MESSAGE_SENDER = "MESSAGE_SENDER";


    public static final String NATIONALITY="NATIONALITY";

    public static final String JOB_PREFERENCES="JOB_PREFERENCES";

    public static final String NOTIFICATION_PREFERENCES="NOTIFICATION_PREFERENCES";
    public static final String NOTIFICATION_PROFILE="NOTIFICATION_PROFILE";
    public static final String NOTIFICATION_JOB="NOTIFICATION_JOB";
    public static final String NOTIFICATION_CALENDAR="NOTIFICATION_CALENDAR";
    public static final String NOTIFICATION_NETWORK="NOTIFICATION_NETWORK";
    public static final String NOTIFICATION_FEED="NOTIFICATION_FEED";
    public static final String NOTIFICATION_MESSAGE="NOTIFICATION_MESSAGE";

    public static final String HAS_DRIVE="HAS_DRIVE";
    public static final String FOLDER="FOLDER";
    public static final String FILE="FILE";

    public static final String HAS_REVIEWS="HAS_REVIEWS";
    public static final String FEATURE_REVIEW="FEATURE_REVIEW";

    public static final String ARTICLE_AUTHOR="ARTICLE_AUTHOR";
    public static final String ARTICLE_SOURCE="ARTICLE_SOURCE";
    public static final String ARTICLE_CATEGORY="ARTICLE_CATEGORY";
    public static final String ARTICLE_SAVED = "ARTICLE_SAVED";
    public static final String ARTICLE_VIEWED = "ARTICLE_VIEWED";

    public static final String CEO = "CEO";
    public static final String COMPANY_DEPARTMENT = "COMPANY_DEPARTMENT";
    public static final String HAS_DEPARTMENT = "HAS_DEPARTMENT";
    public static final String HAS_EMPLOYEE = "HAS_EMPLOYEE";
    public static final String DEPARTMENT = "DEPARTMENT";
    public static final String PART_OF = "PART_OF";
    public static final String DEPARTMENT_LOCATION = "DEPARTMENT_LOCATION";
    public static final String DEPARTMENT_MANAGER = "DEPARTMENT_MANAGER";
    public static final String DEPARTMENT_EMPLOYEE = "DEPARTMENT_EMPLOYEE";
    public static final String MANAGER_OF = "MANAGER_OF";
    public static final String EMPLOYEE = "EMPLOYEE";
    public static final String EMPLOYEE_ROLE = "EMPLOYEE_ROLE";


    public static final String SERVICE_PROVIDER = "SERVICE_PROVIDER";
    public static final String SERVICE_STAFF = "SERVICE_STAFF";

    public static final String LOCATION = "LOCATION";


    public static final String TrendingFollowing = "TrendingFollowing";

    public static boolean hasDataResource(List<String> labels) {
        return labels.contains(DataResource);
    }

    public static boolean hasComment(List<String> labels) {
        return labels.contains(Comment);
    }

    public static boolean hasImageResource(List<String> labels) {
        return labels.contains(ImageResource);
    }
}
