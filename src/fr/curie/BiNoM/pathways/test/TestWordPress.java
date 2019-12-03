package fr.curie.BiNoM.pathways.test;

import java.net.MalformedURLException;
import java.util.List;

import redstone.xmlrpc.XmlRpcFault;

import net.bican.wordpress.Page;
import net.bican.wordpress.PageDefinition;
import net.bican.wordpress.Wordpress;

public class TestWordPress
{
	public static void main(String[] args)
	{
		try
                {
			test("binom", "dsf6%sk9Idqqf", "http://tranquilpc-ixls.curie.fr/blog//xmlrpc.php");
                }
		catch (MalformedURLException e)
                {
                	e.printStackTrace();
                }
                catch (XmlRpcFault e)
                {
                	e.printStackTrace();
                }
	}
	static void test(String username, String password, String xmlRpcUrl) throws MalformedURLException, XmlRpcFault
	{
		Wordpress wp = new Wordpress(username, password, xmlRpcUrl);
		List<Page> recentPosts = wp.getRecentPosts(10);
		System.out.println("Here are the ten recent posts:");
		for (Page page : recentPosts) {
			System.out.println(page.getPostid() + ":" + page.getTitle());
		}
		List<PageDefinition> pages = wp.getPageList();
		System.out.println("Here are the pages:");
		for (PageDefinition pageDefinition : pages) {
			System.out.println(pageDefinition.getPage_title());
		}
		System.out.println("Posting a test (draft) page from a previous page...");
		Page recentPost = wp.getRecentPosts(1).get(0);
		recentPost.setTitle("Test Page");
		recentPost.setDescription("Test description");
		String result = wp.newPost(recentPost, false);
		System.out.println("new post page id: " + result);
		System.out.println("\nThat's all for now.");
	}
}
