import dbutils

configs = {"fs.azure.account.auth.type": "OAuth",
           "fs.azure.account.oauth.provider.type": "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider",
           "fs.azure.account.oauth2.client.id": "ea07640e-c333-4be8-9bc3-d817f30d5fcb",
           "fs.azure.account.oauth2.client.secret": "r01iYou6G_O8GiU7II-E-4BOqp2-ei6VeX",
           "fs.azure.account.oauth2.client.endpoint": "https://login.microsoftonline.com/7631cd62-5187-4e15-8b8e-ef653e366e7a/oauth2/token",
           "fs.azure.createRemoteFileSystemDuringInitialization": "true"}

# Optionally, you can add <directory-name> to the source URI of your mount point.
dbutils.fs.mount(
  source = "abfss://naidacontainer@naidaaccount.dfs.core.windows.net/",
  mount_point = "/mnt/naidacontainer",
  extra_configs = configs)

display(dbutils.fs.ls('/mnt/naidacontainer'))