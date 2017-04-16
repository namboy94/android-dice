#!/usr/bin/python
"""
Copyright 2014 Thomas Schmidt, 2017 Hermann Krumrey

Licensed under the Apache License, Version 2.0 (the 'License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
"""

# Uploads an apk to the Google Play Store

import argparse
import httplib2
from apiclient.discovery import build
from oauth2client import client
from oauth2client.service_account import ServiceAccountCredentials

# Declare command-line flags.
argparser = argparse.ArgumentParser(add_help=False)
argparser.add_argument("package_name", help="The package name. Example: com.android.sample")
argparser.add_argument("track", help="The Track on which to release the APK file."
                                     "Can be 'alpha', beta', 'production' or 'rollout'")
argparser.add_argument("changes_en", help="The changes to the app in this version")
argparser.add_argument("service_account_email", help="The Service Account Email Address for the "
                                                     "Google API for this project")
argparser.add_argument("key_file", help="The path to the p12 key file generated for the"
                                        "service account")
argparser.add_argument("apk_file", nargs='?', help="The path to the APK file to upload.")


def main():
  # Load the key in PKCS 12 format that you downloaded from the Google APIs
  # Console when you created your Service account.
  # f = file('key.p12', 'rb')
  # key = f.read()
  # f.close()

  # Process flags and read their values.
  flags = argparser.parse_args()

  if flags.track not in ["alpha", "beta", "production", "rollout"]:
      print("Invalid track")

  # Create an httplib2.Http object to handle our HTTP requests and authorize it
  # with the Credentials. Note that the first parameter, service_account_name,
  # is the Email address created for the Service account. It must be the email
  # address associated with the key that was created.
  credentials = ServiceAccountCredentials.from_p12_keyfile(
      flags.service_account_email,
      "key.p12",
      scopes="https://www.googleapis.com/auth/androidpublisher")

  http = httplib2.Http()
  http = credentials.authorize(http)
  service = build("androidpublisher", "v2", http=http)

  package_name = flags.package_name
  apk_file = flags.apk_file

  try:

    edit_request = service.edits().insert(body={}, packageName=package_name)
    result = edit_request.execute()
    edit_id = result["id"]

    apk_response = service.edits().apks().upload(
        editId=edit_id,
        packageName=package_name,
        media_body=apk_file).execute()

    print("Version code " + str(apk_response['versionCode']) + " has been uploaded")

    track_response = service.edits().tracks().update(
        editId=edit_id,
        track=flags.track,
        packageName=package_name,
        body={u'versionCodes': [apk_response["versionCode"]]}).execute()

    print("Track " + str(track_response["track"]) + " is set for version code(s) "
                                               "" +  str(track_response["versionCodes"]))

    with open(flags.changes_en) as f:
        changes_en = f.read()

    listing_response = service.edits().apklistings().update(
        editId=edit_id, packageName=package_name, language='en-US',
        apkVersionCode=apk_response['versionCode'],
        body={'recentChanges': changes_en}).execute()

    print("Listing for language " + str(listing_response['language']) + " was updated.")

    commit_request = service.edits().commit(
        editId=edit_id, packageName=package_name).execute()

    print ("Edit " + commit_request['id'] + " has been committed")

  except client.AccessTokenRefreshError:
    print("The credentials have been revoked or expired, please re-run the "
          "application to re-authorize")

if __name__ == '__main__':
  main()
