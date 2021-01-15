configs = {"fs.azure.account.auth.type": "OAuth",
           "fs.azure.account.oauth.provider.type": "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider",
           "fs.azure.account.oauth2.client.id": "be4c1d34-ac5f-45bf-bd26-216f8a4273f8",
           "fs.azure.account.oauth2.client.secret": "FFo3uHZx5QxOFF4~~JJ9I4_zO5j.NM.2If",
           "fs.azure.account.oauth2.client.endpoint": "https://login.microsoftonline.com/7631cd62-5187-4e15-8b8e-ef653e366e7a/oauth2/token",
           "fs.azure.createRemoteFileSystemDuringInitialization": "true"}

dbutils.fs.mount(
  source = "abfss://naida9@naida9.dfs.core.windows.net/",
  mount_point = "/mnt/naida9/",
  extra_configs = configs)

#для демонстрації заеманченої діру
display(dbutils.fs.ls('/mnt/naida9'))