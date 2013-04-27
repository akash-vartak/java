/**
 * ConfluenceSoapService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.seibert_media.kunden.rpc.soap_axis.confluenceservice_v2;

@SuppressWarnings({"rawtypes"})
public interface ConfluenceSoapService extends java.rmi.Remote {

	public java.lang.String[] getPermissions(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSearchResult[] search(
		java.lang.String in0,
		java.lang.String in1,
		int in2
	) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSearchResult[] search(java.lang.String in0, java.lang.String in1, java.util.HashMap in2, int in3)
		throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSpace getSpace(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteComment getComment(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteServerInfo getServerInfo(java.lang.String in0) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemotePageSummary[] getChildren(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteUser getUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public java.lang.String login(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemotePage getPage(
		java.lang.String in0,
		java.lang.String in1,
		java.lang.String in2
	) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemotePage getPage(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public boolean isPluginEnabled(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteClusterInformation getClusterInformation(java.lang.String in0) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteAttachment[] getAttachments(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteAttachment addAttachment(
		java.lang.String in0, long in1, com.atlassian.confluence.rpc.soap.beans.RemoteAttachment in2,
		byte[] in3
	) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteAttachment addAttachment(
		java.lang.String in0,
		com.atlassian.confluence.rpc.soap.beans.RemoteAttachment in1,
		byte[] in2
	)
		throws java.rmi.RemoteException;

	public boolean removeAttachment(java.lang.String in0, long in1, java.lang.String in2) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteContentPermissionSet getContentPermissionSet(java.lang.String in0, long in1, java.lang.String in2)
		throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteContentPermissionSet[] getContentPermissionSets(
		java.lang.String in0,
		long in1
	) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteComment[] getComments(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteComment addComment(java.lang.String in0, com.atlassian.confluence.rpc.soap.beans.RemoteComment in1)
		throws java.rmi.RemoteException;

	public boolean removeComment(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemotePageSummary[] getDescendents(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemotePageSummary[] getAncestors(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public boolean logout(java.lang.String in0) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteAttachment getAttachment(
		java.lang.String in0,
		long in1,
		java.lang.String in2,
		int in3
	) throws java.rmi.RemoteException;

	public boolean removeUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public void addUser(java.lang.String in0, com.atlassian.confluence.rpc.soap.beans.RemoteUser in1, java.lang.String in2) throws java.rmi.RemoteException;

	public void addUser(
		java.lang.String in0,
		com.atlassian.confluence.rpc.soap.beans.RemoteUser in1,
		java.lang.String in2,
		boolean in3
	) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteLabel[] getRelatedLabels(
		java.lang.String in0,
		java.lang.String in1,
		int in2
	) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSpaceSummary[] getSpaces(java.lang.String in0) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemotePageSummary[] getPages(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public java.lang.String getSpaceStatus(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public java.lang.String[] getGroups(java.lang.String in0) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemotePermission[] getPagePermissions(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public boolean setContentPermissions(
		java.lang.String in0,
		long in1,
		java.lang.String in2,
		com.atlassian.confluence.rpc.soap.beans.RemoteContentPermission[] in3
	)
		throws java.rmi.RemoteException;

	public boolean moveAttachment(java.lang.String in0, long in1, java.lang.String in2, long in3, java.lang.String in4) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteComment editComment(java.lang.String in0, com.atlassian.confluence.rpc.soap.beans.RemoteComment in1)
		throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemotePageSummary[] getTopLevelPages(
		java.lang.String in0,
		java.lang.String in1
	) throws java.rmi.RemoteException;

	public byte[] getAttachmentData(java.lang.String in0, long in1, java.lang.String in2, int in3) throws java.rmi.RemoteException;

	public boolean isWatchingSpace(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean isWatchingPage(java.lang.String in0, long in1, java.lang.String in2) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteContentSummaries getTrashContents(java.lang.String in0, java.lang.String in1, int in2, int in3)
		throws java.rmi.RemoteException;

	public boolean emptyTrash(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public boolean removeSpace(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public boolean deactivateUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public boolean reactivateUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public boolean removeGroup(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean removeAllPermissionsForGroup(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteLabel[] getRecentlyUsedLabels(java.lang.String in0, int in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteLabel[] getRecentlyUsedLabelsInSpace(
		java.lang.String in0,
		java.lang.String in1,
		int in2
	) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteLabel[] getMostPopularLabels(java.lang.String in0, int in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteLabel[] getMostPopularLabelsInSpace(
		java.lang.String in0,
		java.lang.String in1,
		int in2
	) throws java.rmi.RemoteException;

	public boolean addGroup(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public boolean addUserToGroup(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean installPlugin(java.lang.String in0, java.lang.String in1, byte[] in2) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSpaceGroup getSpaceGroup(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public boolean setSpaceStatus(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public java.lang.String[] getPermissionsForUser(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean removeSpaceGroup(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSpaceGroup[] getSpaceGroups(java.lang.String in0) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteLabel[] getRelatedLabelsInSpace(
		java.lang.String in0,
		java.lang.String in1,
		java.lang.String in2,
		int in3
	)
		throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSpace[] getSpacesContainingContentWithLabel(
		java.lang.String in0,
		java.lang.String in1
	) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSpace[] getSpacesWithLabel(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteLabel[] getLabelsByDetail(
		java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3,
		java.lang.String in4
	) throws java.rmi.RemoteException;

	public boolean movePageToTopLevel(java.lang.String in0, long in1, java.lang.String in2) throws java.rmi.RemoteException;

	public java.lang.String renderContent(java.lang.String in0, java.lang.String in1, long in2, java.lang.String in3) throws java.rmi.RemoteException;

	public java.lang.String renderContent(
		java.lang.String in0,
		java.lang.String in1,
		long in2,
		java.lang.String in3,
		java.util.HashMap in4
	) throws java.rmi.RemoteException;

	public java.lang.String exportSpace(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public java.lang.String exportSpace(java.lang.String in0, java.lang.String in1, java.lang.String in2, boolean in3) throws java.rmi.RemoteException;

	public boolean removeUserFromGroup(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean hasUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public boolean setEnableWysiwyg(java.lang.String in0, boolean in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSpace addSpace(java.lang.String in0, com.atlassian.confluence.rpc.soap.beans.RemoteSpace in1)
		throws java.rmi.RemoteException;

	public java.lang.String[] getUserGroups(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemotePage updatePage(
		java.lang.String in0, com.atlassian.confluence.rpc.soap.beans.RemotePage in1,
		com.atlassian.confluence.rpc.soap.beans.RemotePageUpdateOptions in2
	) throws java.rmi.RemoteException;

	public boolean isPluginInstalled(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemotePageSummary getPageSummary(java.lang.String in0, java.lang.String in1, java.lang.String in2)
		throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemotePageSummary getPageSummary(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemotePageHistory[] getPageHistory(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public boolean movePage(java.lang.String in0, long in1, long in2, java.lang.String in3) throws java.rmi.RemoteException;

	public boolean removePage(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemotePage storePage(java.lang.String in0, com.atlassian.confluence.rpc.soap.beans.RemotePage in1)
		throws java.rmi.RemoteException;

	public boolean purgeFromTrash(java.lang.String in0, java.lang.String in1, long in2) throws java.rmi.RemoteException;

	public boolean watchPage(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public boolean watchPageForUser(java.lang.String in0, long in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean watchSpace(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public boolean removePageWatch(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public boolean removeSpaceWatch(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public boolean removePageWatchForUser(java.lang.String in0, long in1, java.lang.String in2) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteUser[] getWatchersForPage(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public boolean isWatchingSpaceForType(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteUser[] getWatchersForSpace(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSpaceSummary[] getSpacesInGroup(
		java.lang.String in0,
		java.lang.String in1
	) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSpace addSpaceWithDefaultPermissions(
		java.lang.String in0,
		com.atlassian.confluence.rpc.soap.beans.RemoteSpace in1
	)
		throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSpace storeSpace(java.lang.String in0, com.atlassian.confluence.rpc.soap.beans.RemoteSpace in1)
		throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSpaceGroup addSpaceGroup(
		java.lang.String in0,
		com.atlassian.confluence.rpc.soap.beans.RemoteSpaceGroup in1
	)
		throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSpace addPersonalSpaceWithDefaultPermissions(
		java.lang.String in0, com.atlassian.confluence.rpc.soap.beans.RemoteSpace in1,
		java.lang.String in2
	) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSpace addPersonalSpace(
		java.lang.String in0,
		com.atlassian.confluence.rpc.soap.beans.RemoteSpace in1,
		java.lang.String in2
	)
		throws java.rmi.RemoteException;

	public java.lang.String[] getSpaceLevelPermissions(java.lang.String in0) throws java.rmi.RemoteException;

	public boolean addPermissionToSpace(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3) throws java.rmi.RemoteException;

	public boolean addGlobalPermissions(java.lang.String in0, java.lang.String[] in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean addGlobalPermission(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean addAnonymousUsePermission(java.lang.String in0) throws java.rmi.RemoteException;

	public boolean addAnonymousViewUserProfilePermission(java.lang.String in0) throws java.rmi.RemoteException;

	public boolean removeAnonymousViewUserProfilePermission(java.lang.String in0) throws java.rmi.RemoteException;

	public boolean removeGlobalPermission(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean addPermissionsToSpace(
		java.lang.String in0,
		java.lang.String[] in1,
		java.lang.String in2,
		java.lang.String in3
	) throws java.rmi.RemoteException;

	public boolean removePermissionFromSpace(
		java.lang.String in0,
		java.lang.String in1,
		java.lang.String in2,
		java.lang.String in3
	) throws java.rmi.RemoteException;

	public boolean editUser(java.lang.String in0, com.atlassian.confluence.rpc.soap.beans.RemoteUser in1) throws java.rmi.RemoteException;

	public boolean isActiveUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public java.lang.String[] getActiveUsers(java.lang.String in0, boolean in1) throws java.rmi.RemoteException;

	public boolean changeMyPassword(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean changeUserPassword(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean setUserInformation(java.lang.String in0, com.atlassian.confluence.rpc.soap.beans.RemoteUserInformation in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteUserInformation getUserInformation(
		java.lang.String in0,
		java.lang.String in1
	) throws java.rmi.RemoteException;

	public boolean setUserPreferenceBoolean(java.lang.String in0, java.lang.String in1, java.lang.String in2, boolean in3) throws java.rmi.RemoteException;

	public boolean getUserPreferenceBoolean(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean setUserPreferenceLong(java.lang.String in0, java.lang.String in1, java.lang.String in2, long in3) throws java.rmi.RemoteException;

	public long getUserPreferenceLong(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean setUserPreferenceString(
		java.lang.String in0,
		java.lang.String in1,
		java.lang.String in2,
		java.lang.String in3
	) throws java.rmi.RemoteException;

	public java.lang.String getUserPreferenceString(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean hasGroup(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;

	public boolean addProfilePicture(
		java.lang.String in0,
		java.lang.String in1,
		java.lang.String in2,
		java.lang.String in3,
		byte[] in4
	) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteBlogEntry getBlogEntryByDayAndTitle(
		java.lang.String in0,
		java.lang.String in1,
		int in2,
		java.lang.String in3
	)
		throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteBlogEntry getBlogEntryByDateAndTitle(
		java.lang.String in0, java.lang.String in1, int in2, int in3, int in4,
		java.lang.String in5
	) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteBlogEntry getBlogEntry(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteBlogEntrySummary[] getBlogEntries(
		java.lang.String in0,
		java.lang.String in1
	) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteBlogEntry storeBlogEntry(
		java.lang.String in0,
		com.atlassian.confluence.rpc.soap.beans.RemoteBlogEntry in1
	)
		throws java.rmi.RemoteException;

	public java.lang.String exportSite(java.lang.String in0, boolean in1) throws java.rmi.RemoteException;

	public boolean flushIndexQueue(java.lang.String in0) throws java.rmi.RemoteException;

	public boolean clearIndexQueue(java.lang.String in0) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteNodeStatus[] getClusterNodeStatuses(java.lang.String in0) throws java.rmi.RemoteException;

	public boolean importSpace(java.lang.String in0, byte[] in1) throws java.rmi.RemoteException;

	public boolean setEnableAnonymousAccess(java.lang.String in0, boolean in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteLabel[] getLabelsById(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSearchResult[] getLabelContentById(java.lang.String in0, long in1) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSearchResult[] getLabelContentByName(
		java.lang.String in0,
		java.lang.String in1
	) throws java.rmi.RemoteException;

	public com.atlassian.confluence.rpc.soap.beans.RemoteSearchResult[] getLabelContentByObject(
		java.lang.String in0,
		com.atlassian.confluence.rpc.soap.beans.RemoteLabel in1
	)
		throws java.rmi.RemoteException;

	public boolean addLabelByName(java.lang.String in0, java.lang.String in1, long in2) throws java.rmi.RemoteException;

	public boolean addLabelById(java.lang.String in0, long in1, long in2) throws java.rmi.RemoteException;

	public boolean addLabelByObject(java.lang.String in0, com.atlassian.confluence.rpc.soap.beans.RemoteLabel in1, long in2) throws java.rmi.RemoteException;

	public boolean addLabelByNameToSpace(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean removeLabelByName(java.lang.String in0, java.lang.String in1, long in2) throws java.rmi.RemoteException;

	public boolean removeLabelById(java.lang.String in0, long in1, long in2) throws java.rmi.RemoteException;

	public boolean removeLabelByObject(java.lang.String in0, com.atlassian.confluence.rpc.soap.beans.RemoteLabel in1, long in2) throws java.rmi.RemoteException;

	public boolean removeLabelByNameFromSpace(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean removeAnonymousUsePermission(java.lang.String in0) throws java.rmi.RemoteException;

	public boolean addAnonymousPermissionToSpace(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean addAnonymousPermissionsToSpace(java.lang.String in0, java.lang.String[] in1, java.lang.String in2) throws java.rmi.RemoteException;

	public boolean removeAnonymousPermissionFromSpace(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;

	public java.lang.String convertWikiToStorageFormat(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
}
