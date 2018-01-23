using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using CryptoNews.Models;
using System.Net.Http;
using System.Net.Http.Headers;
using Newtonsoft.Json.Linq;
using System.Net;

namespace CryptoNews.Services
{
    public class PostsRepository
    {

    
        private static List<BlogPost> blogPosts = new List<BlogPost>();
      

        public async Task<List<BlogPost>> GetAllPostsAsync()
        {
            var blogWebsites = App.database.GetAllBlogWebsites();

            foreach (BlogWebsite blogWebsite in blogWebsites)
            {
                HttpClient httpClient = new HttpClient();
                httpClient.BaseAddress = new Uri(blogWebsite.Url);
                httpClient.DefaultRequestHeaders.Accept.Clear();
                httpClient.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                var response = await httpClient.GetAsync("/wp-json/wp/v2/posts?_embed");
                if (response.IsSuccessStatusCode)
                {
                    var content = response.Content;
                    var json = await content.ReadAsStringAsync();
                    var postsJson = JsonConvert.DeserializeObject<dynamic>(json);
                    JArray jsonArray = JArray.Parse(postsJson.ToString());

                    foreach (var item in jsonArray)
                    {
                        try
                        {
                            var blogPost = new BlogPost();
                            blogPost.Id = Guid.NewGuid();
                            blogPost.BlogWebsiteName = blogWebsite.Name;
                            blogPost.Title = WebUtility.HtmlDecode(item.Value<JObject>("title").Value<string>("rendered"));
                            blogPost.Link = item.Value<string>("link");

                            var tempExcerpt = item.Value<JObject>("excerpt").Value<string>("rendered");
                            if (tempExcerpt.Length > 200)
                            {
                                blogPost.Excerpt = tempExcerpt.Substring(0, 200);
                            }
                            else
                            {
                                blogPost.Excerpt = tempExcerpt;
                            }

                            blogPost.Date = item.Value<DateTime>("date");
                            blogPost.FeaturedImage =
                                item?.Value<JObject>("_embedded")?.Value<JArray>("wp:featuredmedia")[0]
                                    ?.Value<string>("source_url") ?? " ";
                            //if (blogPosts.Find(s => s.Title == blogPost.Title) == null)
                            //{
                                blogPosts.Add(blogPost);
                            //}

                        }
                        catch (Exception e)
                        {
                            continue;
                        }
                    }
                }
                httpClient.Dispose();
            }
            
            //foreach (var blogPostX in db.BlogPosts)
            //{
            //    db.BlogPosts.Remove(blogPostX);
            //}
            await App.database.AddUpdatedBlogPosts(blogPosts);
            var OrderedBlogPosts = blogPosts.OrderByDescending(s => s.Date).ToList();
            return OrderedBlogPosts;
        }
    }
}
